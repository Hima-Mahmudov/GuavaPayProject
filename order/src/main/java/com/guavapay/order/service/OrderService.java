package com.guavapay.order.service;

import com.guavapay.order.controller.dto.order.OrderLiteResponse;
import com.guavapay.order.controller.dto.order.OrderResponse;
import com.guavapay.order.controller.dto.order.OrderSubmittingResponse;
import com.guavapay.order.entity.Order;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface OrderService {
    OrderResponse create(Order order);

    OrderResponse update(Order order, Long orderId) throws AccessDeniedException;

    boolean deleteById(Long orderId);

    OrderResponse getOrderById(Long orderId);

    List<OrderLiteResponse> getOrderList(Long userId);

    OrderSubmittingResponse submitOrder(Long orderId);
}
