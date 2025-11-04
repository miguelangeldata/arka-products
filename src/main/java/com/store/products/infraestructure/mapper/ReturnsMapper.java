package com.store.products.infraestructure.mapper;

import com.store.products.domain.models.Returns;
import com.store.products.infraestructure.adapaters.in.dto.ReturnedItem;
import com.store.products.infraestructure.adapaters.out.entity.ReturnItemEntity;
import com.store.products.infraestructure.adapaters.out.entity.ReturnsEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReturnsMapper {

    public ReturnsEntity domainToEntity(Returns returns){
        List<ReturnItemEntity> items=returns.getItems()
                .stream().map(this::domainToEntity).toList();
        return new ReturnsEntity(
                returns.getId(),
                returns.getOrderId(),
                returns.getReturnedAt(),
                returns.getUserId(),
                returns.getUserEmail(),
                returns.getReason(),
                items,
                returns.getStatus()
        );
    }
    public Returns entityToDomain(ReturnsEntity returnsEntity){
        List<ReturnedItem> items=returnsEntity.getItems()
                .stream().map(this::entityToDomain).toList();
        Returns returns =new Returns();
        returns.setId(returnsEntity.getId());
        returns.setReturnedAt(returnsEntity.getReturnedAt());
        returns.setUserId(returnsEntity.getUserId());
        returns.setUserEmail(returnsEntity.getUserEmail());
        returns.setReason(returnsEntity.getReason());
        returns.setItems(items);
        returns.setStatus(returnsEntity.getStatus());
        return returns;
    }
    private ReturnedItem entityToDomain(ReturnItemEntity itemEntity){
        return new ReturnedItem(itemEntity.getProductId(), itemEntity.getQuantity());
    }
    private ReturnItemEntity domainToEntity(ReturnedItem returnedItem){
        return new ReturnItemEntity(returnedItem.productId(), returnedItem.quantity());
    }
}
