package com.example.ecommerce.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestCreateOrder {
    @NotBlank(message = "customerName required")
    private String customerName;
    @NotBlank(message = "customerEmail required")
    @Email(message = "Enter a valid email")
    private String customerEmail;
}
