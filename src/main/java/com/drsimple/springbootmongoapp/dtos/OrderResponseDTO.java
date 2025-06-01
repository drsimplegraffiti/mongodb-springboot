package com.drsimple.springbootmongoapp.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private String id;
    private String product;
    private Double price;
    private String userId;
}