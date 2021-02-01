package com.guavapay.gateway.entity;

import com.guavapay.gateway.entity.enums.Status;
import lombok.Data;

import java.util.Date;


@Data
public class BaseEntity {

    private Long id;

    private Date created;

    private Date updated;

    private Status status;
}
