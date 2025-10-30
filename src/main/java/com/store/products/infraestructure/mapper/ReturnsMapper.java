package com.store.products.infraestructure.mapper;

import com.store.products.domain.models.Returns;
import com.store.products.infraestructure.adapaters.out.entity.ReturnsEntity;
import org.springframework.stereotype.Component;

@Component
public class ReturnsMapper {

    public ReturnsEntity domainToEntity(Returns returns){
        return new ReturnsEntity(
                returns.getId(),
                returns.getReturnedAt(),
                returns.getUserId(),
                returns.getReason(),
                returns.getItems()
        );
    }
    public Returns entityToDomain(ReturnsEntity returnsEntity){
        Returns returns =new Returns();
        returns.setId(returnsEntity.getId());
        returns.setReturnedAt(returnsEntity.getReturnedAt());
        returns.setUserId(returnsEntity.getUserId());
        returns.setReason(returnsEntity.getReason());
        returns.setItems(returnsEntity.getItems());
        return returns;
    }
}
