package com.guavapay.order.controller.dto.order;

import com.guavapay.order.entity.enums.Status;
import lombok.Data;

import java.util.Date;

@Data
public class OrderLiteResponse {

    private Long id;

    private Date created;

    private Status status;
}
