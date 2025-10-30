package com.store.products.application.services;

import com.store.products.domain.exceptions.InsufficientStockException;
import com.store.products.domain.exceptions.ProductNotFoundException;
import com.store.products.domain.exceptions.ServiceTemporarilyUnavailableException;
import com.store.products.domain.models.StockHistory;
import com.store.products.domain.models.StockHistoryReason;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

import com.store.products.domain.models.Product;
import com.store.products.domain.ports.in.ProductUsesCases;
import com.store.products.domain.ports.out.persistence.IProductPersistencePort;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductUsesCases {

    private final IProductPersistencePort persistencePort;
    private final StockHistoryService stockHistoryService;
    private static final String CB_NAME = "products-cb";


    @Override
    @CircuitBreaker(name = CB_NAME, fallbackMethod = "productMonoFallback")
    public Mono<Product> createProduct(Product product) {
        return persistencePort.save(product);
    }


    @Override
    @CircuitBreaker(name = CB_NAME, fallbackMethod = "getProductByIdFallback")
    public Mono<Product> getProductById(Long id) {
        return  persistencePort.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product Not Found With Id"+id)));
    }
    @Override
    @CircuitBreaker(name = CB_NAME, fallbackMethod = "productMonoFallback")
    public Mono<Product> updateProduct(Long productId,Product product) {
        return getProductById(productId)
                .flatMap(productFound ->{
                    productFound.setName(product.getName());
                    productFound.setCategory(product.getCategory());
                    productFound.setPrice(product.getPrice());
                    productFound.setDescription(product.getDescription());
                    return persistencePort.save(productFound);
                });

    }

    @Override
    @CircuitBreaker(name = CB_NAME, fallbackMethod = "productFluxFallback")
    public Flux<Product> getAllProducts() {
        return  persistencePort.findAll();
    }

    @Override
    @CircuitBreaker(name = CB_NAME, fallbackMethod = "voidFallback")
    public Mono<Void> deleteProduct(Long id) {
        return persistencePort.delete(id);
    }
    @Override
    @CircuitBreaker(name = CB_NAME, fallbackMethod = "voidFallback")
    public Mono<Void>  incrementStock(Long productId, Integer quantity) {
        return getProductById(productId)
                .flatMap(product -> {
                    Integer previousStock = product.getTotalStock();
                    Integer newStock = product.increaseStock(quantity);
                    StockHistory newStockHistory = new StockHistory(product.getSku(), previousStock, newStock, StockHistoryReason.INCREMENT);

                    Mono<Product> saveProduct = persistencePort.save(product);
                    Mono<StockHistory> saveHistory = stockHistoryService.registerStockHistory(newStockHistory);
                    return Mono.when(saveProduct, saveHistory);
                });
    }

    @Override
    @CircuitBreaker(name = CB_NAME, fallbackMethod = "voidFallback")
    public Mono<Void>  decrementStock(Long productId, Integer quantity) {
        return getProductById(productId)
                .flatMap(product -> {
                    Integer previousStock = product.getTotalStock();
                    Integer newStock = product.decreaseStock(quantity);
                    StockHistory newStockHistory = new StockHistory(product.getSku(), previousStock, newStock, StockHistoryReason.DECREMENT);

                    Mono<Product> saveProduct = persistencePort.save(product);
                    Mono<StockHistory> saveHistory = stockHistoryService.registerStockHistory(newStockHistory);

                    return Mono.when(saveProduct, saveHistory);
                });
    }
    @Override
    public Mono<Void> recoveryStockFromReturn(Long productId, Integer quantity) {
        return getProductById(productId)
                .flatMap(product -> {
                    Integer previousStock = product.getTotalStock();
                    Integer newStock = product.increaseStock(quantity);
                    StockHistory newStockHistory = new StockHistory(product.getSku(), previousStock, newStock, StockHistoryReason.STOCK_RECOVERY_FROM_RETURNS);

                    Mono<Product> saveProduct = persistencePort.save(product);
                    Mono<StockHistory> saveHistory = stockHistoryService.registerStockHistory(newStockHistory);

                    return Mono.when(saveProduct, saveHistory);
                });
    }

    @Override
    @CircuitBreaker(name = CB_NAME, fallbackMethod = "reserveStockFallback")
    public Mono<Void> reserveStock(Long productId, Integer quantity) {
        if (quantity <= 0) {
            return Mono.error(new IllegalArgumentException("Quantity must be positive"));
        }
        return getProductById(productId)
                .flatMap(product -> {
                    Integer available = product.getAvailableStock();
                    if (available == null || available < quantity) {
                        return Mono.error(new InsufficientStockException(
                                "Insufficient available stock for SKU: " + productId + ". Available: " + available
                        ));
                    }
                    product.reserveStock(quantity);
                    return persistencePort.save(product);
                })
                .then();
    }

    @Override
    @CircuitBreaker(name = CB_NAME, fallbackMethod = "getAvailableStockFallback")
    public Mono<Integer> getAvailableStock(Long productId) {
        return getProductById(productId)
                .flatMap(product -> {
                    Integer availableStock= product.getAvailableStock();
                    return Mono.just(availableStock);
                })
                .switchIfEmpty(Mono.just(0));
    }

    @Override
    @CircuitBreaker(name = CB_NAME, fallbackMethod = "voidFallback")
    public Mono<Void> recoveryStock(Long productId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            return Mono.error(new IllegalArgumentException("Quantity must be positive"));
        }
        return getProductById(productId)
                .flatMap(product -> {
                    Integer reserved = product.getReservedStock();

                    if (reserved == null || reserved < quantity) {
                        return Mono.error(new IllegalArgumentException(
                                "Cannot recover more than reserved for SKU: " + productId + ". Reserved: " + reserved
                        ));
                    }
                    product.recoverReservedStock(quantity);
                    return persistencePort.save(product);
                })
                .then();
    }

    private Mono<Void> reserveStockFallback(Long productId, Integer quantity, Throwable t) {
        System.err.println("Circuit Breaker Open [" + CB_NAME + "]. Reason: " + t.getMessage());
        return Mono.error(new ServiceTemporarilyUnavailableException(
                "Service is currently unavailable due to internal system failure. Please try again later."
        ));
    }

    private Mono<Void> voidFallback(Long productId, Integer quantity, Throwable t) {
        System.err.println("Circuit Breaker Open [" + CB_NAME + "]. Void operation failure. Reason: " + t.getMessage());
        return Mono.error(new ServiceTemporarilyUnavailableException(
                "Service is temporarily unavailable. The operation could not be completed."
        ));
    }
    private Mono<Product> productMonoFallback(Long productId, Product product, Throwable t) {
        System.err.println("Circuit Breaker Open [" + CB_NAME + "]. Product operation failure. Reason: " + t.getMessage());
        return Mono.error(new ServiceTemporarilyUnavailableException(
                "Service is currently unavailable: cannot create or update product."
        ));
    }

    private Mono<Product> getProductByIdFallback(Long id, Throwable t) {
        return Mono.error(new ServiceTemporarilyUnavailableException(
                "Service is currently unavailable: cannot retrieve product details."
        ));
    }
    private Flux<Product> productFluxFallback(Throwable t) {
        System.err.println("Circuit Breaker Open [" + CB_NAME + "]. Flux operation failure. Reason: " + t.getMessage());
        return Flux.error(new ServiceTemporarilyUnavailableException(
                "Service is currently unavailable: cannot retrieve product list."
        ));
    }
    private Mono<Integer> getAvailableStockFallback(Long productId, Throwable t) {
        System.err.println("Circuit Breaker Open [" + CB_NAME + "]. Stock check failure. Reason: " + t.getMessage());
        return Mono.error(new ServiceTemporarilyUnavailableException(
                "Service is currently unavailable: cannot check available stock."
        ));
    }
}
