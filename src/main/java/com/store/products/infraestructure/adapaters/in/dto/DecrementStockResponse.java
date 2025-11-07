package com.store.products.infraestructure.adapaters.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Confirmation response for stock decrement operations.")
public class DecrementStockResponse {

    @Schema(description = "Status message confirming the operation.", example = "Stock was decremented successfully")
    private String message;

    @Schema(description = "ID of the product whose stock was changed.", example = "101")
    private Long productId;
}
