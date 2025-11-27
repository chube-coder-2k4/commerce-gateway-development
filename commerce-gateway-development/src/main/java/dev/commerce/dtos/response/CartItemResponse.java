package dev.commerce.dtos.response;

import lombok.*;

import java.util.UUID;
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CartItemResponse {
    private UUID id;
    private UUID productId;
    private String productName;
    private int quantity;
    private double price;
    private double totalPrice;
    private UUID createdBy;
    private UUID updatedBy;
}
