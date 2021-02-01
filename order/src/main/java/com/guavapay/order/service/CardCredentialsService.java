package com.guavapay.order.service;

import com.guavapay.order.controller.dto.order.OrderSubmittingResponse;

public interface CardCredentialsService {
    OrderSubmittingResponse createOrderSubmittingResponse();

    String randomAlphaNumeric(int count);
}
