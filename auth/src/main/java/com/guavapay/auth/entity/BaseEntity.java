package com.guavapay.auth.entity;

import com.guavapay.auth.entity.enums.Status;
import lombok.Data;

import java.util.Date;


@Data
public class BaseEntity {

    private Long id;

    private Date created;

    private Date updated;

    private Status status;
}
