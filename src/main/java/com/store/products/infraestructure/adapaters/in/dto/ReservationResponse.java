package com.store.products.infraestructure.adapaters.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response confirming stock reservation or recovery operations.")
public record ReservationResponse(
        @Schema(description = "Status message detailing the reservation result.",
                example = "Stock successfully reserved for product 101. Quantity: 5")
        String message,

        @Schema(description = "ID of the product involved.", example = "101")
        Long productId,

        @Schema(description = "The quantity that was reserved or recovered.", example = "5")
        Integer quantity) {
}