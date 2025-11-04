package com.store.products.infraestructure.adapaters.out.entity;

import com.store.products.domain.models.ReturnsStatus;
import com.store.products.infraestructure.adapaters.in.dto.ReturnedItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "returns")
public class ReturnsEntity {
    @Id
    private UUID id;
    @Column("order_id")
    private String orderId;
    @Column("returned_at")
    private LocalDateTime returnedAt;
    @Column("user_id")
    private String userId;
    @Column("user_email")
    private String userEmail;
    private String reason;
    @MappedCollection(idColumn = "return_id")
    private List<ReturnItemEntity> items;
    private ReturnsStatus status;
}
