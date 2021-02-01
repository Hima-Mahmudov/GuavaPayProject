package com.guavapay.users.controller.dto;

import com.guavapay.users.entity.enums.Status;
import lombok.Data;

import java.util.Date;

@Data
public class UserResponse {

    private Long id;

    private Date created;

    private Date updated;

    private Status status;
    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String token;

    private String role;
}
