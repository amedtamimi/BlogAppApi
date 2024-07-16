package com.springboot.blog.springbootrestapi.controller;

import com.springboot.blog.springbootrestapi.dto.LoginDto;
import com.springboot.blog.springbootrestapi.dto.RegisterDto;
import com.springboot.blog.springbootrestapi.service.AuthService;
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
@PostMapping(value = {"/login","/signIn"})
    public ResponseEntity<String> loginUser(@RequestBody LoginDto loginDto){
        String response = authService.loginUser(loginDto);
        return ResponseEntity.ok(response);
    }

    //add the register user method
    @PostMapping(value = {"/register","/signUp"})
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto){
        String response = authService.registerUser(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }





}
