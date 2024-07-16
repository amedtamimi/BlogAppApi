package com.springboot.blog.springbootrestapi.service;

import com.springboot.blog.springbootrestapi.dto.LoginDto;
import com.springboot.blog.springbootrestapi.dto.RegisterDto;

public interface AuthService {
    String registerUser(RegisterDto registerDto);
    String loginUser(LoginDto loginDto);
}
