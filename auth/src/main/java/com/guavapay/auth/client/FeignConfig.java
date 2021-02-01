package com.guavapay.auth.client;

import feign.Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Value("${openfeign.clients.users.url}")
    private String usersBaseUrl;

    @Autowired
    @Qualifier(value = "springMvcContract")
    private SpringMvcContract contract;

    @Autowired
    private FeignErrorDecoder errorDecoder;

    @Bean
    UsersClient usersClient() {
        return Feign.builder()
                .errorDecoder(errorDecoder)
                .contract(contract)
                .target(UsersClient.class, usersBaseUrl);
    }

    @Bean
    public SpringMvcContract springMvcContract() {
        return new SpringMvcContract();
    }
}
