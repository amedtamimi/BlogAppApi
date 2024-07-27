package com.springboot.blog.springbootrestapi.controller;

import com.springboot.blog.springbootrestapi.dto.JwtAuthResponse;
import com.springboot.blog.springbootrestapi.dto.LoginDto;
import com.springboot.blog.springbootrestapi.dto.RegisterDto;
import com.springboot.blog.springbootrestapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

   @Operation(
           summary = "Login User REST API",
           description = "Login User REST API is used to login user into the system"
   )
@PostMapping(value = {"/login","/signIn"})
    public ResponseEntity<JwtAuthResponse> loginUser(@RequestBody LoginDto loginDto){
        String token = authService.loginUser(loginDto);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }


    //add the register user method
    @Operation(
            summary = "Register User REST API",
            description = "Register User REST API is used to register user into database"
    )
    @PostMapping(value = {"/register","/signUp"})
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto){
        String response = authService.registerUser(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }





}
