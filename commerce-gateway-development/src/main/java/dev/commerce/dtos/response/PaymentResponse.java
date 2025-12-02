package dev.commerce.dtos.response;

import dev.commerce.dtos.common.PaymentMethod;
import dev.commerce.dtos.common.PaymentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponse(
        UUID id,
        UUID orderId,
        PaymentMethod provider,
        double amount,
        String transactionId,
        PaymentStatus status,
        LocalDateTime paidAt,
        UUID createdBy,
        UUID updatedBy
) {

}
