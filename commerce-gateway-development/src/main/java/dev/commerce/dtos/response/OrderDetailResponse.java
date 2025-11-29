package dev.commerce.dtos.response;

import dev.commerce.dtos.common.OrderStatus;
import dev.commerce.dtos.common.PaymentMethod;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderDetailResponse(
        UUID id,
        String orderCode,
        double totalAmount,
        OrderStatus status,
        PaymentMethod paymentMethod,
        String shippingAddress,
        LocalDateTime createdAt,
        List<OrderItemResponse> items,
        UUID usersId,
        UUID createdBy,
        UUID updatedBy

) {
}
