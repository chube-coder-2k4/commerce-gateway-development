package dev.commerce.dtos.response;

import java.util.UUID;

public record PaymentUrlResponse(
    String paymentUrl,
    String orderCode,
    UUID createdBy,
    UUID updatedBy
) {
}
