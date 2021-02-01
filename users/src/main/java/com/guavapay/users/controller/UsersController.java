package com.guavapay.users.controller;

import com.guavapay.users.controller.dto.UserResponse;
import com.guavapay.users.entity.User;
import com.guavapay.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UsersController {

    private final UserService userService;

    @GetMapping("/users/{username}")
    public ResponseEntity<UserResponse> loadUserByUsername(@PathVariable(name = "username") String username) {
        User user = userService.findByUsername(username);
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setCreated(user.getCreated());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setId(user.getId());
        userResponse.setRole(user.getRole().getName());
        userResponse.setStatus(user.getStatus());
        userResponse.setToken(user.getToken());
        userResponse.setUpdated(user.getUpdated());
        userResponse.setPassword(user.getPassword());

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/users/{userId}")
    public void updateToken(@RequestHeader(name = "Authorization") String token,
                            @PathVariable(name = "userId") Long userId) {
        userService.updateToken(token, userId);
    }

}
