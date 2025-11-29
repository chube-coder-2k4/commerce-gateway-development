package dev.commerce.mappers;

import dev.commerce.dtos.response.OrderDetailResponse;
import dev.commerce.dtos.response.OrderItemResponse;
import dev.commerce.dtos.response.OrderResponse;
import dev.commerce.entitys.Order;
import dev.commerce.entitys.OrderItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    // OrderItem -> OrderItemResponse
    default OrderItemResponse toOrderItemResponse(OrderItem item) {
        return new OrderItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getUnitPrice() * item.getQuantity()
        );
    }

    // List<OrderItem> -> List<OrderItemResponse>
    default List<OrderItemResponse> toOrderItemResponses(List<OrderItem> items) {
        return items.stream()
                .map(this::toOrderItemResponse)
                .toList();
    }

    // Order -> OrderResponse
    default OrderResponse toOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderCode(order.getOrderCode())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus().name())
                .usersId(order.getUsers().getId())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    // Order + List<OrderItem> -> OrderDetailResponse (detailed)
    default OrderDetailResponse toOrderDetailResponse(Order order, List<OrderItem> items) {
        return new OrderDetailResponse(
                order.getId(),
                order.getOrderCode(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getPaymentMethod(),
                order.getShippingAddress(),
                order.getCreatedAt(),
                toOrderItemResponses(items),
                order.getUsers().getId(),
                order.getCreatedBy(),
                order.getUpdatedBy()
        );
    }
}
