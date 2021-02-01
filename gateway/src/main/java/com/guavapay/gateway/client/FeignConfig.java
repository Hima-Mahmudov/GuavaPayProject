package com.guavapay.gateway.client;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Value("${openfeign.clients.auth.url}")
    private String usersBaseUrl;

    @Autowired
    @Qualifier(value = "springMvcContract")
    private SpringMvcContract contract;

    @Autowired
    private FeignErrorDecoder errorDecoder;

    @Bean
    AuthClient authClient() {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .errorDecoder(errorDecoder)
                .contract(contract)
                .target(AuthClient.class, usersBaseUrl);
    }

    @Bean
    public SpringMvcContract springMvcContract() {
        return new SpringMvcContract();
    }
}
