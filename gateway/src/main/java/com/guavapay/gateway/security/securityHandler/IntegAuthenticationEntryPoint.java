package com.guavapay.gateway.security.securityHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guavapay.gateway.exception.response.Error;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component("IntegAuthenticationEntryPoint")
public class IntegAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {

        Error responseApi = new Error(HttpStatus.FORBIDDEN.getReasonPhrase(), "Access Denied, Invalidate token");

        response.setContentType("application/json");
        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, responseApi);
        out.flush();
    }
}
