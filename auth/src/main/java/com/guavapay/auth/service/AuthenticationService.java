package com.guavapay.auth.service;

import com.guavapay.auth.controller.dto.AuthenticationRequestDto;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    ResponseEntity<?> authenticate(AuthenticationRequestDto requestBody, HttpServletRequest request, HttpServletResponse response);
}
