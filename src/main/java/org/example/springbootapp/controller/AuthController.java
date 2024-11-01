package org.example.springbootapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springbootapp.dto.user.UserLoginRequestDto;
import org.example.springbootapp.dto.user.UserLoginResponseDto;
import org.example.springbootapp.dto.user.UserRegistrationRequestDto;
import org.example.springbootapp.dto.user.UserResponseDto;
import org.example.springbootapp.exception.RegistrationException;
import org.example.springbootapp.security.AuthenticationService;
import org.example.springbootapp.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto responseDto) {
        return authenticationService.authenticate(responseDto);
    }

    @PostMapping("/register")
    public UserResponseDto register(@Valid @RequestBody UserRegistrationRequestDto request) throws RegistrationException {
        return userService.register(request);
    }
}
