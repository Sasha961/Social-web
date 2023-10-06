package com.example.demo.controller;

import com.example.demo.dto.security.AuthErrorDto;
import com.example.demo.dto.security.JWTRequestDto;
import com.example.demo.dto.security.JWTResponseDto;
import com.example.demo.service.security.JWT.JwtUser;
import com.example.demo.service.security.JWT.JwtUtil;
import com.example.demo.service.security.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JWTRequestDto jwtRequestDto){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequestDto.getUsername(), jwtRequestDto.getPassword()));
        }catch (BadCredentialsException ex){
            return new ResponseEntity<>(new AuthErrorDto(HttpStatus.UNAUTHORIZED.value(), "don't correct login or password"), HttpStatus.UNAUTHORIZED);
        }
        JwtUser jwtUser = userService.loadUserByUsername(jwtRequestDto.getUsername());

        String token = jwtUtil.generateToken(jwtUser);
        return ResponseEntity.ok(new JWTResponseDto(token));
    }
}
