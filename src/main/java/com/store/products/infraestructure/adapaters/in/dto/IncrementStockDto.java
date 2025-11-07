package com.store.products.infraestructure.adapaters.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Payload to specify the quantity for stock increase operations.")
public record IncrementStockDto(
        @Schema(description = "The number of units to add to the existing stock.", example = "10")
        Integer quantity) {
}
