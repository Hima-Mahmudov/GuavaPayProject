package com.guavapay.users.service.impl;

import com.guavapay.users.entity.User;
import com.guavapay.users.exception.DataNotFoundException;
import com.guavapay.users.repository.UserRepository;
import com.guavapay.users.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        return result;
    }

    @Override
    public void updateToken(String token, Long id) {
        Optional<User> user = userRepository.findById(id);
        user.orElseThrow(() -> new DataNotFoundException("user not found by given id"));
        user.get().setToken(token);
        userRepository.save(user.get());
    }
}
