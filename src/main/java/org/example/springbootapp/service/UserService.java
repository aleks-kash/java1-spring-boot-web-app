package org.example.springbootapp.service;

import org.example.springbootapp.dto.user.UserRegistrationRequestDto;
import org.example.springbootapp.dto.user.UserResponseDto;
import org.example.springbootapp.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException;
}
