package com.store.products.infraestructure.mapper;
import org.springframework.stereotype.Component;

import com.store.products.domain.models.Product;
import com.store.products.infraestructure.adapaters.in.dto.ProductRequestDto;
import com.store.products.infraestructure.adapaters.in.dto.ProductResponseDto;
import com.store.products.infraestructure.adapaters.out.entity.ProductEntity;

@Component
public class ProductMapper {
    public Product toDomain(ProductEntity productEntity){
        return new Product(
        productEntity.getId(),
        productEntity.getSku(),
        productEntity.getName(), 
        productEntity.getCategory(), 
        productEntity.getDescription(),
        productEntity.getPrice(), 
        productEntity.getTotalStock(),
        productEntity.getReservedStock());
    };

    public ProductEntity toEntity(Product product){
        return new ProductEntity(
            product.getId(),
            product.getSku(),
            product.getName(),
            product.getCategory(), 
            product.getDescription(), 
            product.getPrice(), 
            product.getTotalStock(),
            product.getReservedStock());
    }

    /*
     * Web Mapper
    */

    public Product toDomain(ProductRequestDto productRequestDto){
        Product newProduct=new Product();
                newProduct.setSku(productRequestDto.getSku());
                newProduct.setName(productRequestDto.getName());
                newProduct.setCategory(productRequestDto.getCategory());
                newProduct.setDescription(productRequestDto.getDescription());
                newProduct.setPrice(productRequestDto.getPrice());
                newProduct.setTotalStock(productRequestDto.getStock());
                return newProduct;

    }
    public ProductResponseDto toResponseDto(Product product){
        return new ProductResponseDto(
            product.getId(),
            product.getSku(),
            product.getName(),
            product.getCategory(),
            product.getDescription(),
            product.getPrice(),
            product.getTotalStock());
    }

}
