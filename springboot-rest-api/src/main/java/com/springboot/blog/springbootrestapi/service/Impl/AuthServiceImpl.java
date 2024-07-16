package com.springboot.blog.springbootrestapi.service.Impl;

import com.springboot.blog.springbootrestapi.dto.LoginDto;
import com.springboot.blog.springbootrestapi.dto.RegisterDto;
import com.springboot.blog.springbootrestapi.entity.Role;
import com.springboot.blog.springbootrestapi.entity.User;
import com.springboot.blog.springbootrestapi.exception.BlogApiException;
import com.springboot.blog.springbootrestapi.repository.RoleRepository;
import com.springboot.blog.springbootrestapi.repository.UserRepository;
import com.springboot.blog.springbootrestapi.service.AuthService;
import com.springboot.blog.springbootrestapi.utils.PasswordGeneratorEncoder;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager  authenticationManager;
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           ModelMapper modelMapper,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String registerUser(RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Username is already taken");
        }
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email is already taken");
        }
        User user = mapToUserEntity(registerDto);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();

        roles.add(userRole);
        user.setRole(roles);


        userRepository.save(user);

        return "User registered successfully";
    }

    @Override
    public String loginUser(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
         SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User logged in successfully";
    }

    private User mapToUserEntity(RegisterDto registerDto){
        return modelMapper.map(registerDto, User.class);
    }
}
