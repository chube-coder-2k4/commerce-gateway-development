package dev.commerce.dtos.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CartResponse {

    private UUID id;
    private double totalPrice;
    private UUID userId;
    private List<CartItemResponse> cartItems;
    private UUID createdBy;
    private UUID updatedBy;
}
