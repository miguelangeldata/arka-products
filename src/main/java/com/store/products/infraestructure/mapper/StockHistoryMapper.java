package com.store.products.infraestructure.mapper;

import com.store.products.domain.models.StockHistory;
import com.store.products.infraestructure.adapaters.out.entity.StockHistoryEntity;
import org.springframework.stereotype.Component;

@Component
public class StockHistoryMapper {

    public StockHistory stockHistoryEntityToDomain(StockHistoryEntity stockHistory){
        return new StockHistory(
          stockHistory.getId(),
          stockHistory.getProductSKU(),
          stockHistory.getCreateAt(),
          stockHistory.getPreviousStock(),
          stockHistory.getNewStock(),
          stockHistory.getReason()
        );
    }
    public StockHistoryEntity stockHistoryToEntity(StockHistory stockHistory){
        return new StockHistoryEntity(
                stockHistory.id(),
                stockHistory.productSKU(),
                stockHistory.createAt(),
                stockHistory.previousStock(),
                stockHistory.newStock(),
                stockHistory.reason()
        );
    }
}
