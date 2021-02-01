package com.guavapay.order.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.guavapay.order.controller.dto.order.*;
import com.guavapay.order.entity.CardCredentials;
import com.guavapay.order.entity.Order;
import com.guavapay.order.entity.Role;
import com.guavapay.order.entity.User;
import com.guavapay.order.entity.enums.CardPeriod;
import com.guavapay.order.entity.enums.CardType;
import com.guavapay.order.entity.enums.Status;
import com.guavapay.order.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
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
    private OrderCreationRequest orderCreationRequest = new OrderCreationRequest();
    private OrderUpdateRequest orderUpdateRequest = new OrderUpdateRequest();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    OrderServiceImpl orderService;

    @InjectMocks
    OrderController orderController;

    private MockMvc mockMvc;

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
        order.setCardCredentials(cardCredentials);
        orderList.add(order);
        optionalOrderList = Optional.of(orderList);
        orderResponse.setCardType(cardType);
        orderResponse.setCardPeriod(cardPeriod.getIntValue());
        orderResponse.setCodeword(codeword);
        orderResponse.setUrgent(urgent);
        orderResponse.setCartHolderName(cardHolderName);
        orderCreationRequest.setCardType(cardType);
        orderCreationRequest.setCardPeriod(cardPeriod.getIntValue());
        orderCreationRequest.setCodeword(codeword);
        orderCreationRequest.setUrgent(urgent);
        orderCreationRequest.setCardHolderFirstname(firstName);
        orderCreationRequest.setCardHolderLastname(lastName);
        orderUpdateRequest.setCardType(cardType);
        orderUpdateRequest.setCardPeriod(cardPeriod.getIntValue());
        orderUpdateRequest.setCodeword(codeword);
        orderUpdateRequest.setUrgent(urgent);
        orderUpdateRequest.setCardHolderFirstname(firstName);
        orderUpdateRequest.setCardHolderLastname(lastName);
        orderLiteResponse.setId(orderId);
        orderLiteResponse.setStatus(status);
        orderLiteResponse.setCreated(create);
        orderSubmittingResponse.setAccountNumber(accountNumber);
        orderSubmittingResponse.setCardNumber(carNumber);
        orderLiteResponseList.add(orderLiteResponse);
        optionalOrder = Optional.of(order);
        optionalOrderWithSubmittedTrue = Optional.of(orderWithSubmittedTrue);

        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void create() throws Exception {
        //given
        when(orderService.create(any(Order.class))).thenReturn(orderResponse);
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJSON = objectWriter.writeValueAsString(orderCreationRequest);

        //when
        MvcResult mvcResult = mockMvc.perform(post("/users/{userId}/orders", userId)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJSON))
                .andExpect(status().isOk())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();


        //then
        assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(orderResponse));
    }

    @Test
    public void orderList() throws Exception {
        //given
        when(orderService.getOrderList(anyLong())).thenReturn(orderLiteResponseList);

        //when
        MvcResult mvcResult = mockMvc.perform(get("/users/{userId}/orders", userId))
                .andExpect(status().isOk())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();


        //then
        assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(orderLiteResponseList));
    }

    @Test
    public void deleteById() throws Exception {
        //given
        when(orderService.deleteById(anyLong())).thenReturn(true);

        //when
        MvcResult mvcResult = mockMvc.perform(delete("/orders/{orderId}", orderId))
                .andExpect(status().isOk())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();


        //then
        assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(true));
    }

    @Test
    public void findById() throws Exception {
        //given
        when(orderService.getOrderById(anyLong())).thenReturn(orderResponse);

        //when
        MvcResult mvcResult = mockMvc.perform(get("/orders/{orderId}", orderId))
                .andExpect(status().isOk())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();


        //then
        assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(orderResponse));
    }

    @Test
    public void update() throws Exception {
        //given
        when(orderService.update(any(Order.class),anyLong())).thenReturn(orderResponse);
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJSON = objectWriter.writeValueAsString(orderUpdateRequest);

        //when
        MvcResult mvcResult = mockMvc.perform(put("/orders/{orderId}", orderId)
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJSON))
                .andExpect(status().isOk())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();


        //then
        assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(orderResponse));
    }

    @Test
    public void submit() throws Exception {
        //given
        when(orderService.submitOrder(anyLong())).thenReturn(orderSubmittingResponse);

        //when
        MvcResult mvcResult = mockMvc.perform(post("/orders/{orderId}", orderId))
                .andExpect(status().isOk())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();


        //then
        assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(orderSubmittingResponse));
    }
}