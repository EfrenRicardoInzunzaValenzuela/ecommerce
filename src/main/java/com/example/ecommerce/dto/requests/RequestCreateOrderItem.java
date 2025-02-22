package com.example.ecommerce.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestCreateOrderItem {
    @NotNull(message = "orderId required")
    @Positive(message = "orderId must be a positive number")
    private Long orderId;

    @NotNull(message = "productId required")
    @Positive(message = "productId must be a positive number")
    private Long productId;

    @NotNull(message = "quantity required")
    @Min(value = 1, message = "quantity must be at least 1")
    private Integer quantity;

}
