package com.store.products.domain.models;

import com.store.products.infraestructure.adapaters.in.dto.ReturnedItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Returns {
    private UUID id;
    private String orderId;
    private LocalDateTime returnedAt=LocalDateTime.now();
    private String userId;
    private String userEmail;
    private String reason;
    private List<ReturnedItem> items;
    private ReturnsStatus status=ReturnsStatus.RECEIVED;

    public Returns(String userId, String orderId,String userEmail, String reason, List<ReturnedItem> items) {
        this.userEmail=userEmail;
        this.orderId=orderId;
        this.userId = userId;
        this.reason = reason;
        this.items = items;
    }
    public void switchToStockRecovery(){
        if (this.status.equals(ReturnsStatus.RECEIVED)){
            this.status=ReturnsStatus.STOCK_RECOVERY;
        }
    }
    public void switchToRejected(){
        if (this.status.equals(ReturnsStatus.RECEIVED)){
            this.status=ReturnsStatus.REJECTED;
        }
    }
}
