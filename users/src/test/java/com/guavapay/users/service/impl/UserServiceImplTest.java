package com.guavapay.users.service.impl;

import com.guavapay.users.entity.Order;
import com.guavapay.users.entity.Role;
import com.guavapay.users.entity.User;
import com.guavapay.users.entity.enums.CardPeriod;
import com.guavapay.users.entity.enums.CardType;
import com.guavapay.users.entity.enums.Status;
import com.guavapay.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private static final Long orderId = 1L;
    private static final Long userId = 1L;
    private static final Long roleId = 1L;
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
    private User user = new User();
    private Order order =  new Order();
    private Role role = new Role();

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

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
    }

    @Test
    public void findByUsername() {
        //given
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        //when
        User test = userService.findByUsername(username);

        //then
        assertThat(user).isEqualTo(test);
    }
}