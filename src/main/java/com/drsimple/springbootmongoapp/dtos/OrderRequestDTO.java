package com.drsimple.springbootmongoapp.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {
    @NotBlank(message = "Product name is required")
    private String product;

    @Min(value = 1, message = "Price must be greater than 0")
    private Double price;

    @NotBlank(message = "UserId is required")
    private String userId;
}