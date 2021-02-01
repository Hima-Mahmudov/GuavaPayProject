package com.guavapay.order.controller.dto.order;

import com.guavapay.order.entity.enums.CardType;
import lombok.Data;

@Data
public class OrderResponse {

    private CardType cardType;

    private String cartHolderName;

    private int cardPeriod;

    private boolean urgent;

    private String codeword;
}
