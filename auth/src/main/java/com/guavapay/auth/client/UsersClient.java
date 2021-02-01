package com.guavapay.auth.client;

import com.guavapay.auth.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "${openfeign.clients.users.name}", configuration = FeignConfig.class, url = "http://localhost:8012/")
public interface UsersClient {
    @RequestMapping(method = RequestMethod.GET, value = "/users/{username}")
    ResponseEntity<User> findUser(@PathVariable(name = "username") String username);

    @RequestMapping(method = RequestMethod.POST, value = "/users/{userId}")
    void updateToken(@RequestHeader("Authorization") String token,
                     @PathVariable(name = "userId") Long userId);
}
