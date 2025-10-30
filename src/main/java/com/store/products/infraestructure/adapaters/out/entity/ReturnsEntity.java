package com.store.products.infraestructure.adapaters.out.entity;

import com.store.products.infraestructure.adapaters.in.dto.ReturnedItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
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
    private LocalDateTime returnedAt;
    private String userId;
    private String reason;
    private List<ReturnedItem> items;
}
