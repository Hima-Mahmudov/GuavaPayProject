package com.guavapay.order.service.impl;

import com.guavapay.order.controller.dto.order.OrderSubmittingResponse;
import com.guavapay.order.service.CardCredentialsService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class CardCredentialsServiceServiceImpl implements CardCredentialsService {
    private static final long smallest = 1000_0000_0000_0000L;
    private static final long biggest = 9999_9999_9999_9999L;
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Override
    public OrderSubmittingResponse createOrderSubmittingResponse() {
        OrderSubmittingResponse orderSubmittingResponse = new OrderSubmittingResponse();
        orderSubmittingResponse.setCardNumber(ThreadLocalRandom.current().nextLong(smallest, biggest + 1));
        orderSubmittingResponse.setAccountNumber(randomAlphaNumeric(16));

        return orderSubmittingResponse;
    }


    @Override
    public String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
