package com.guavapay.order.controller;

import com.guavapay.order.controller.dto.order.*;
import com.guavapay.order.entity.Order;
import com.guavapay.order.entity.User;
import com.guavapay.order.entity.enums.CardPeriod;
import com.guavapay.order.entity.enums.Status;
import com.guavapay.order.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/users/{userId}/orders")
    @ApiOperation(value = "Create a new order by user id", response = OrderResponse.class)
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderCreationRequest request, @PathVariable(name = "userId") Long userId) {
        Order order = new Order();
        order.setCreated(new Date());
        order.setUpdated(new Date());
        order.setUrgent(request.isUrgent());
        order.setCodeword(request.getCodeword());
        order.setCardHolderName(request.getCardHolderFirstname() + " " + request.getCardHolderLastname());
        order.setCardPeriod(CardPeriod.fromInteger(request.getCardPeriod()));
        order.setCardType(request.getCardType());
        order.setSubmitted(false);
        order.setStatus(Status.ACTIVE);
        User user = new User();
        user.setId(userId);
        order.setUser(user);

        OrderResponse response = orderService.create(order);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}/orders")
    @ApiOperation(value = "Get order list by user id", response = OrderLiteResponse.class)
    public ResponseEntity<List<OrderLiteResponse>> orderList(@PathVariable(name = "userId") Long userId) {

        List<OrderLiteResponse> response = orderService.getOrderList(userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/orders/{orderId}")
    @ApiOperation(value = "Delete order by id", response = Boolean.class)
    public ResponseEntity<Boolean> delete(@PathVariable(name = "orderId") Long orderId) {

        Boolean response = orderService.deleteById(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders/{orderId}")
    @ApiOperation(value = "Get order by id", response = OrderResponse.class)
    public ResponseEntity<OrderResponse> findById(@PathVariable(name = "orderId") Long orderId) {

        OrderResponse response = orderService.getOrderById(orderId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/orders/{orderId}")
    @ApiOperation(value = "Update order by user id", response = OrderResponse.class)
    public ResponseEntity<OrderResponse> update(@Valid @RequestBody OrderUpdateRequest request, @PathVariable(name = "orderId") Long orderId) throws AccessDeniedException {
        Order order = new Order();

        order.setUrgent(request.isUrgent());
        order.setCodeword(request.getCodeword());
        order.setCardHolderName(request.getCardHolderFirstname() + " " + request.getCardHolderLastname());
        order.setCardPeriod(CardPeriod.fromInteger(request.getCardPeriod()));
        order.setCardType(request.getCardType());
        OrderResponse response = orderService.update(order, orderId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/orders/{orderId}")
    @ApiOperation(value = "Update order by user id", response = OrderResponse.class)
    public ResponseEntity<OrderSubmittingResponse> submit(@PathVariable(name = "orderId") Long orderId) {

        OrderSubmittingResponse response = orderService.submitOrder(orderId);
        return ResponseEntity.ok(response);
    }
}
