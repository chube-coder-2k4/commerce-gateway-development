package dev.commerce.services.impl;

import dev.commerce.dtos.common.OrderStatus;
import dev.commerce.dtos.common.PaymentMethod;
import dev.commerce.dtos.request.OrderRequest;
import dev.commerce.dtos.response.OrderDetailResponse;
import dev.commerce.dtos.response.OrderResponse;
import dev.commerce.entitys.*;
import dev.commerce.exception.ResourceNotFoundException;
import dev.commerce.mappers.OrderMapper;
import dev.commerce.repositories.jpa.*;
import dev.commerce.services.OrderService;
import dev.commerce.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AuthenticationUtils utils;

    @Override
    public OrderDetailResponse createOrder(OrderRequest orderRequest) {
        Users user = utils.getCurrentUser();
        Cart cart = cartRepository.findByUsers(user).orElseThrow(() -> new ResourceNotFoundException("Cart is Empty"));
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        if(cartItems.isEmpty()) {
            throw new ResourceNotFoundException("Cart is Empty");
        }
        Order order = new Order();
        order.setUsers(user);
        order.setOrderCode("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(cart.getTotalPrice());
        order.setPaymentMethod(orderRequest.getPaymentMethod() != null ? orderRequest.getPaymentMethod() : PaymentMethod.COD);
        order.setShippingAddress(orderRequest.getShippingAddress() != null ? orderRequest.getShippingAddress() : user.getAddress());
        order.setPaidAt(LocalDateTime.now());
        orderRepository.save(order);

        List<OrderItem> orderItem = cartItems.stream().map(ci -> {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(ci.getProduct());
            item.setQuantity(ci.getQuantity());
            item.setUnitPrice(ci.getPrice());
            return orderItemRepository.save(item);
        }).toList();
        cartItemRepository.deleteAll(cartItems);
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);

        return orderMapper.toOrderDetailResponse(order,orderItem);
    }

    @Override
    public List<OrderResponse> getUserOrders() {
        Users user = utils.getCurrentUser();
        return orderRepository.findByUsers(user).stream().map(orderMapper::toOrderResponse).toList();
    }

    @Override
    public List<OrderDetailResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> {
            List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
            return orderMapper.toOrderDetailResponse(order, orderItems);
        }).toList();
    }

    @Override
    public OrderResponse updateStatus(UUID orderId, OrderStatus status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        orderRepository.save(order);
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public OrderResponse cancelOrder(UUID orderId) {
        Order order = getOrderById(orderId);
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
        return orderMapper.toOrderResponse(order);

    }

    @Override
    public OrderDetailResponse getOrderDetails(UUID orderId) {
        Order order = getOrderById(orderId);
        List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
        return orderMapper.toOrderDetailResponse(order, orderItems);
    }

    private Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

}
