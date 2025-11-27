package dev.commerce.dtos.request;

import lombok.*;

import java.util.UUID;
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CartItemRequest {
    private UUID productId;
    private int quantity;
}
