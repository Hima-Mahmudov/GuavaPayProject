package com.guavapay.order.repository;

import com.guavapay.order.entity.CardCredentials;
import com.guavapay.order.entity.Order;
import com.guavapay.order.entity.Role;
import com.guavapay.order.entity.User;
import com.guavapay.order.entity.enums.CardPeriod;
import com.guavapay.order.entity.enums.CardType;
import com.guavapay.order.entity.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderRepositoryTest {

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
    private Order order =  new Order();
    private Role role = new Role();
    List<Order> orderList = new ArrayList<>();
    Optional<List<Order>> optionalOrderList;


    @Mock
    OrderRepository orderRepository;

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
        cardCredentials.setOrder(order);
        order.setCardCredentials(cardCredentials);
        orderList.add(order);
        optionalOrderList = Optional.of(orderList);
    }

    @Test
    public void findAllByUser_Id() {
        //given
        when(orderRepository.findAllByUser_Id(anyLong())).thenReturn(optionalOrderList);

        //when
        Optional<List<Order>> test = orderRepository.findAllByUser_Id(userId);

        //then
        assertThat(optionalOrderList.get()).isEqualTo(test.get());
    }
}