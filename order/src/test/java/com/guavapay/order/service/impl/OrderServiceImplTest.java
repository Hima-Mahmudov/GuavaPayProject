package com.guavapay.order.service.impl;


import com.guavapay.order.controller.dto.order.OrderLiteResponse;
import com.guavapay.order.controller.dto.order.OrderResponse;
import com.guavapay.order.controller.dto.order.OrderSubmittingResponse;
import com.guavapay.order.entity.CardCredentials;
import com.guavapay.order.entity.Order;
import com.guavapay.order.entity.Role;
import com.guavapay.order.entity.User;
import com.guavapay.order.entity.enums.CardPeriod;
import com.guavapay.order.entity.enums.CardType;
import com.guavapay.order.entity.enums.Status;
import com.guavapay.order.exception.DataNotFoundException;
import com.guavapay.order.exception.OrderException;
import com.guavapay.order.repository.CardCredentialsRepository;
import com.guavapay.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    private static final Long orderId = 1L;
    private static final Long userId = 1L;
    private static final Long roleId = 1L;
    private static final Long cardCredentialsId = 1L;
    private static final Date create = new Date();
    private static final Date update = new Date();
    private static final Status status = Status.ACTIVE;
    private static final CardType cardType = CardType.VISA;
    private static final String cardHolderName = "Hikmat Mahmudov";
    private static final CardPeriod cardPeriod = CardPeriod.YEAR;
    private static final boolean urgent = false;
    private static final String codeword = "guavapay";
    private static final boolean submitted = false;
    private static final String username = "Hima";
    private static final String password = "$2y$12$nhSjTV/AeBkDzgyEe5vIbOZhU0efMZ.oBXYhbW6Ish6Feouln938G";
    private static final String firstName = "Hikmat";
    private static final String lastName = "Mahmudov";
    private static final String email = "mahmudov.hima@gmail.com";
    private static final String token = "token";
    private static final String roleName = "ROLE_USER";
    private static final Long carNumber = 1234567812345678L;
    private static final String accountNumber = "1ZFPD6LINC51UXYW";
    private User user = new User();
    private CardCredentials cardCredentials = new CardCredentials();
    private Order order = new Order();
    private Order orderWithSubmittedTrue = new Order();
    private Order orderWithDeletedStatus = new Order();
    private Role role = new Role();
    private List<Order> orderList = new ArrayList<>();
    private Optional<List<Order>> optionalOrderList;
    private OrderResponse orderResponse = new OrderResponse();
    private OrderLiteResponse orderLiteResponse = new OrderLiteResponse();
    private List<OrderLiteResponse> orderLiteResponseList = new ArrayList<>();
    private OrderSubmittingResponse orderSubmittingResponse = new OrderSubmittingResponse();
    private Optional<Order> optionalOrder;
    private Optional<Order> optionalOrderWithSubmittedTrue;


    @Mock
    OrderRepository orderRepository;

    @Mock
    CardCredentialsRepository cardCredentialsRepository;

    @Mock
    CardCredentialsServiceServiceImpl cardCredentialsServiceService;

    @InjectMocks
    OrderServiceImpl orderService;

    @BeforeEach
    public void setUp() throws Exception {
        role.setId(roleId);
        role.setCreated(create);
        role.setUpdated(update);
        role.setStatus(status);
        role.setName(roleName);
        user.setId(userId);
        user.setEmail(email);
        user.setStatus(status);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(password);
        user.setToken(token);
        user.setCreated(create);
        user.setUpdated(update);
        user.setRole(role);
        cardCredentials.setId(cardCredentialsId);
        cardCredentials.setCreated(create);
        cardCredentials.setUpdated(update);
        cardCredentials.setStatus(status);
        cardCredentials.setCardNumber(carNumber);
        cardCredentials.setAccountNumber(accountNumber);
        order.setId(orderId);
        order.setCreated(create);
        order.setUpdated(update);
        order.setUser(user);
        order.setCardType(cardType);
        order.setCardPeriod(cardPeriod);
        order.setCodeword(codeword);
        order.setUrgent(urgent);
        order.setStatus(status);
        order.setSubmitted(submitted);
        order.setCardHolderName(cardHolderName);
        orderWithSubmittedTrue.setId(orderId);
        orderWithSubmittedTrue.setCreated(create);
        orderWithSubmittedTrue.setUpdated(update);
        orderWithSubmittedTrue.setUser(user);
        orderWithSubmittedTrue.setCardType(cardType);
        orderWithSubmittedTrue.setCardPeriod(cardPeriod);
        orderWithSubmittedTrue.setCodeword(codeword);
        orderWithSubmittedTrue.setUrgent(urgent);
        orderWithSubmittedTrue.setStatus(status);
        orderWithSubmittedTrue.setSubmitted(true);
        orderWithSubmittedTrue.setCardHolderName(cardHolderName);
        orderWithDeletedStatus.setId(orderId);
        orderWithDeletedStatus.setCreated(create);
        orderWithDeletedStatus.setUpdated(update);
        orderWithDeletedStatus.setUser(user);
        orderWithDeletedStatus.setCardType(cardType);
        orderWithDeletedStatus.setCardPeriod(cardPeriod);
        orderWithDeletedStatus.setCodeword(codeword);
        orderWithDeletedStatus.setUrgent(urgent);
        orderWithDeletedStatus.setStatus(Status.DELETED);
        orderWithDeletedStatus.setSubmitted(submitted);
        orderWithDeletedStatus.setCardHolderName(cardHolderName);
        cardCredentials.setOrder(order);
        order.setCardCredentials(cardCredentials);
        orderList.add(order);
        optionalOrderList = Optional.of(orderList);
        orderResponse.setCardType(cardType);
        orderResponse.setCardPeriod(cardPeriod.getIntValue());
        orderResponse.setCodeword(codeword);
        orderResponse.setUrgent(urgent);
        orderResponse.setCartHolderName(cardHolderName);
        orderLiteResponse.setId(orderId);
        orderLiteResponse.setStatus(status);
        orderLiteResponse.setCreated(create);
        orderSubmittingResponse.setAccountNumber(accountNumber);
        orderSubmittingResponse.setCardNumber(carNumber);
        orderLiteResponseList.add(orderLiteResponse);
        optionalOrder = Optional.of(order);
        optionalOrderWithSubmittedTrue = Optional.of(orderWithSubmittedTrue);
    }

    @Test
    public void createSuccess() {
        //given
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        //when
        OrderResponse test = orderService.create(order);

        //then
        assertThat(orderResponse).isEqualTo(test);
    }

    @Test
    public void updateSuccess() throws AccessDeniedException {
        //given
        when(orderRepository.findById(anyLong())).thenReturn(optionalOrder);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        //when
        OrderResponse test = orderService.update(order, orderId);

        //then
        assertThat(orderResponse).isEqualTo(test);
    }

    @Test
    public void updateWithThrownDataNotFoundException() {
        //given
        when(orderRepository.findById(anyLong())).thenThrow(DataNotFoundException.class);

        //then
        assertThatThrownBy(() -> orderService.update(order, orderId)).isInstanceOf(DataNotFoundException.class);

    }

    @Test
    public void updateWithThrownAccessDeniedException() {
        //given
        when(orderRepository.findById(anyLong())).thenReturn(optionalOrderWithSubmittedTrue);

        //then
        assertThatThrownBy(() -> orderService.update(order, orderId)).isInstanceOf(AccessDeniedException.class);
    }

    @Test
    public void deleteByIdSuccess() {
        //given
        when(orderRepository.findById(anyLong())).thenReturn(optionalOrder);
        when(orderRepository.save(any(Order.class))).thenReturn(orderWithDeletedStatus);

        //when
        boolean test = orderService.deleteById(orderId);

        //then
        assertThat(true).isEqualTo(test);
    }

    @Test
    public void deleteWithThrownDataNotFoundException() {
        //given
        when(orderRepository.findById(anyLong())).thenThrow(DataNotFoundException.class);

        //then
        assertThatThrownBy(() -> orderService.deleteById(orderId)).isInstanceOf(DataNotFoundException.class);
    }


    @Test
    public void getOrderByIdSuccess() {
        //given
        when(orderRepository.findById(anyLong())).thenReturn(optionalOrder);

        //when
        OrderResponse test = orderService.getOrderById(orderId);

        //then
        assertThat(orderResponse).isEqualTo(test);
    }

    @Test
    public void getOrderByIdWithThrownDataNotFoundException() {
        //given
        when(orderRepository.findById(anyLong())).thenThrow(DataNotFoundException.class);

        //then
        assertThatThrownBy(() -> orderService.getOrderById(orderId)).isInstanceOf(DataNotFoundException.class);
    }

    @Test
    public void getOrderListSuccess() {
        //given
        when(orderRepository.findAllByUser_Id(anyLong())).thenReturn(optionalOrderList);

        //when
        List<OrderLiteResponse> test = orderService.getOrderList(userId);

        //then
        assertThat(orderLiteResponseList).isEqualTo(test);
    }

    @Test
    public void getOrderListWithThrownDataNotFoundException() {
        //given
        when(orderRepository.findAllByUser_Id(anyLong())).thenThrow(DataNotFoundException.class);

        //then
        assertThatThrownBy(() -> orderService.getOrderList(userId)).isInstanceOf(DataNotFoundException.class);
    }

    @Test
    public void submitOrderSuccess() {
        //given
        when(orderRepository.findById(anyLong())).thenReturn(optionalOrder);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(cardCredentialsRepository.save(any(CardCredentials.class))).thenReturn(cardCredentials);
        when(cardCredentialsServiceService.createOrderSubmittingResponse()).thenReturn(orderSubmittingResponse);

        //when
        OrderSubmittingResponse test = orderService.submitOrder(orderId);

        //then
        assertThat(orderSubmittingResponse).isEqualTo(test);
    }

    @Test
    public void submitOrderWithThrownDataNotFoundException() {
        //given
        when(orderRepository.findById(anyLong())).thenThrow(DataNotFoundException.class);

        //then
        assertThatThrownBy(() -> orderService.submitOrder(orderId)).isInstanceOf(DataNotFoundException.class);
    }
    @Test
    public void submitOrderWithThrownOrderException() {
        //given
        when(orderRepository.findById(anyLong())).thenReturn(optionalOrderWithSubmittedTrue);

        //then
        assertThatThrownBy(() -> orderService.submitOrder( orderId)).isInstanceOf(OrderException.class);
    }

}