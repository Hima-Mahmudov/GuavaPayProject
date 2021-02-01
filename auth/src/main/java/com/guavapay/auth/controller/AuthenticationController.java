package com.guavapay.auth.controller;

import com.guavapay.auth.controller.dto.AuthenticationRequestDto;
import com.guavapay.auth.service.AuthenticationService;
import com.guavapay.auth.service.imlp.UserDetailsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Api(value = "GuavaPay project Login")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserDetailsServiceImpl userDetailsService;

    @ApiOperation(value = "Create token")
    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Bad credentials", response = Error.class)
    })
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDto requestBody, HttpServletRequest request, HttpServletResponse response) {
        return authenticationService.authenticate(requestBody, request, response);
    }

    @GetMapping("/getUserDetails/{username}")
    public UserDetails loadUserByUsername(@PathVariable(name = "username") String username) throws UsernameNotFoundException {
        return userDetailsService.loadUserByUsername(username);
    }

}
