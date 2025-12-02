package dev.commerce.services;

import dev.commerce.dtos.response.PaymentResponse;
import dev.commerce.dtos.response.PaymentUrlResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface PaymentService {

    PaymentUrlResponse createPaymentUrl(UUID orderId);
    PaymentResponse handlePaymentCallback(Map<String, String> callbackParams);
    List<PaymentResponse> getUserPayments(UUID userId);
    PaymentResponse getPaymentDetails(UUID paymentId);
}
