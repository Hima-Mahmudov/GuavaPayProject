package com.guavapay.users.service;


import com.guavapay.users.entity.User;

public interface UserService {

    User findByUsername(String username);

    void updateToken(String token, Long id);
}
