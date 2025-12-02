package dev.commerce.dtos.response;

public record PaymentUrlResponse(
    String paymentUrl,
    String orderCode
) {
}
