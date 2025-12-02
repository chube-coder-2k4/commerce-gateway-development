package dev.commerce.services.impl;

import dev.commerce.configurations.VNPayConfig;
import dev.commerce.dtos.response.PaymentResponse;
import dev.commerce.dtos.response.PaymentUrlResponse;
import dev.commerce.repositories.jpa.OrderRepository;
import dev.commerce.repositories.jpa.PaymentRepository;
import dev.commerce.services.PaymentService;
import dev.commerce.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final AuthenticationUtils utils;
    private final VNPayConfig vnPayConfig;


    @Override
    public PaymentUrlResponse createPaymentUrl(UUID orderId) {
        return null;
    }

    @Override
    public PaymentResponse handlePaymentCallback(Map<String, String> vnpParams) {
        return null;
    }

    @Override
    public List<PaymentResponse> getMyPayments() {
        return List.of();
    }

    @Override
    public PaymentResponse getPaymentDetails(UUID paymentId) {
        return null;
    }
}
