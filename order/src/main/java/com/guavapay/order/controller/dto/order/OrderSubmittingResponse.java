package com.guavapay.order.controller.dto.order;

import lombok.Data;

@Data
public class OrderSubmittingResponse {

    private Long cardNumber;

    private String accountNumber;
}
