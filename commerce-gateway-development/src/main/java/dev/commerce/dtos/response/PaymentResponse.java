package dev.commerce.dtos.response;

import java.util.UUID;

public record PaymentResponse(
        UUID id,
        UUID orderId,
        String provider,
        double amount,
        String transactionId,
        String status,
        String paidAt,
        UUID createdBy,
        UUID updatedBy
) {

}
