package dev.commerce.services;

import dev.commerce.dtos.common.OrderStatus;
import dev.commerce.dtos.request.OrderRequest;
import dev.commerce.dtos.response.OrderDetailResponse;
import dev.commerce.dtos.response.OrderResponse;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDetailResponse createOrder(OrderRequest orderRequest);

    List<OrderDetailResponse> getUserOrders();

    List<OrderResponse> getAllOrders();

    OrderResponse updateStatus(UUID orderId, OrderStatus status);

    OrderResponse cancelOrder(UUID orderId);

    OrderDetailResponse getOrderDetails(UUID orderId);

}

