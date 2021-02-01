package com.guavapay.order.controller.dto.order;

import com.guavapay.order.entity.enums.CardType;
import lombok.Data;

@Data
public class OrderCreationRequest {

    private CardType cardType;

    private String cardHolderFirstname;

    private String cardHolderLastname;

    private int cardPeriod;

    private boolean urgent;

    private String codeword;
}
