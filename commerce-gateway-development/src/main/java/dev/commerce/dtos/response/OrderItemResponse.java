package dev.commerce.dtos.response;


import java.util.UUID;

public record OrderItemResponse(
        UUID id,
        UUID productId,
        String productName,
        int quantity,
        double unitPrice,
        double totalPrice
) {
}
