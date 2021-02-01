package com.guavapay.gateway.client;

import com.guavapay.gateway.security.JwtUser;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Headers("Content-Type: application/json")
@FeignClient(name = "${openfeign.clients.auth.name}", configuration = FeignConfig.class)
public interface AuthClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/auth/getUserDetails/{username}")
    JwtUser loadUserByUsername(@PathVariable(name = "username") String username);

}
