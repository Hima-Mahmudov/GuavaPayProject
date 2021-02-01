package com.guavapay.auth.service.imlp;

import com.guavapay.auth.client.UsersClient;
import com.guavapay.auth.controller.dto.AuthenticationRequestDto;
import com.guavapay.auth.entity.User;
import com.guavapay.auth.security.jwt.JwtTokenProvider;
import com.guavapay.auth.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsersClient usersClient;

    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDto requestBody, HttpServletRequest request, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestBody.getUsername(), requestBody.getPassword()));

            User user = usersClient.findUser(requestBody.getUsername()).getBody();
            String token = jwtTokenProvider.createToken(requestBody.getUsername(), user.getRole());
            Map<Object, Object> responseBody = new HashMap<>();
            responseBody.put("username", requestBody.getUsername());
            responseBody.put("token", token);
            usersClient.updateToken(token, user.getId());
            flushCache();
            return ResponseEntity.ok(responseBody);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @CacheEvict(cacheNames = "token", allEntries = true)
    public void flushCache() {
    }
}
