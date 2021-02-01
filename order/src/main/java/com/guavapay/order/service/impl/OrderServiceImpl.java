package com.guavapay.order.service.impl;

import com.guavapay.order.controller.dto.order.OrderLiteResponse;
import com.guavapay.order.controller.dto.order.OrderResponse;
import com.guavapay.order.controller.dto.order.OrderSubmittingResponse;
import com.guavapay.order.entity.CardCredentials;
import com.guavapay.order.entity.Order;
import com.guavapay.order.entity.enums.Status;
import com.guavapay.order.exception.DataNotFoundException;
import com.guavapay.order.exception.OrderException;
import com.guavapay.order.repository.CardCredentialsRepository;
import com.guavapay.order.repository.OrderRepository;
import com.guavapay.order.service.CardCredentialsService;
import com.guavapay.order.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CardCredentialsRepository cardCredentialsRepository;
    private final CardCredentialsService cardCredentialsService;

    @Override
    public OrderResponse create(Order order) {
        Order savedOrder = orderRepository.save(order);
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setCardPeriod(savedOrder.getCardPeriod().getIntValue());
        orderResponse.setCardType(savedOrder.getCardType());
        orderResponse.setCodeword(savedOrder.getCodeword());
        orderResponse.setCartHolderName(savedOrder.getCardHolderName());
        orderResponse.setUrgent(savedOrder.isUrgent());
        return orderResponse;
    }

    @Override
    public OrderResponse update(Order order, Long orderId) throws AccessDeniedException {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        optionalOrder.orElseThrow(() -> new DataNotFoundException("Order not found by given id"));

        if (optionalOrder.get().isSubmitted()) {
            throw new AccessDeniedException("Order already submitted");
        }

        optionalOrder.get().setCardPeriod(order.getCardPeriod());
        optionalOrder.get().setCardType(order.getCardType());
        optionalOrder.get().setCodeword(order.getCodeword());
        optionalOrder.get().setUrgent(order.isUrgent());
        optionalOrder.get().setCardHolderName(order.getCardHolderName());
        optionalOrder.get().setUpdated(new Date());

        Order updatedOrder = orderRepository.save(optionalOrder.get());

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setCardPeriod(updatedOrder.getCardPeriod().getIntValue());
        orderResponse.setCardType(updatedOrder.getCardType());
        orderResponse.setCodeword(updatedOrder.getCodeword());
        orderResponse.setCartHolderName(updatedOrder.getCardHolderName());
        orderResponse.setUrgent(updatedOrder.isUrgent());

        return orderResponse;
    }

    @Override
    public boolean deleteById(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        order.orElseThrow(() -> new DataNotFoundException("Order not found by given id"));

        order.get().setStatus(Status.DELETED);
        orderRepository.save(order.get());
        return true;
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        order.orElseThrow(() -> new DataNotFoundException("Order not found by given id"));

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setCardPeriod(order.get().getCardPeriod().getIntValue());
        orderResponse.setCardType(order.get().getCardType());
        orderResponse.setCodeword(order.get().getCodeword());
        orderResponse.setCartHolderName(order.get().getCardHolderName());
        orderResponse.setUrgent(order.get().isUrgent());

        return orderResponse;
    }

    @Override
    public List<OrderLiteResponse> getOrderList(Long userId) {
        Optional<List<Order>> optionalOrderList = orderRepository.findAllByUser_Id(userId);
        optionalOrderList.orElseThrow(() -> new DataNotFoundException("Orders not found by given user id"));

        List<OrderLiteResponse> orderLiteResponses = new ArrayList<>();

        optionalOrderList.get().forEach(order -> {
            OrderLiteResponse response = new OrderLiteResponse();
            response.setId(order.getId());
            response.setStatus(order.getStatus());
            response.setCreated(order.getCreated());
            orderLiteResponses.add(response);
        });

        return orderLiteResponses;
    }

    @Override
    public OrderSubmittingResponse submitOrder(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        order.orElseThrow(() -> new DataNotFoundException("Order not found by given id"));

        if (order.get().isSubmitted()) {
            throw new OrderException("order already submitted");
        }

        order.get().setSubmitted(true);
        order.get().setStatus(Status.NOT_ACTIVE);

        orderRepository.save(order.get());

        OrderSubmittingResponse response = cardCredentialsService.createOrderSubmittingResponse();

        CardCredentials cardCredentials = new CardCredentials();
        cardCredentials.setOrder(order.get());
        cardCredentials.setAccountNumber(response.getAccountNumber());
        cardCredentials.setCardNumber(response.getCardNumber());
        cardCredentials.setStatus(Status.ACTIVE);
        cardCredentials.setCreated(new Date());
        cardCredentials.setUpdated(new Date());

        cardCredentialsRepository.save(cardCredentials);

        return response;
    }





}
