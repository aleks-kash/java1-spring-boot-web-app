package org.example.springbootapp.service;

import org.example.springbootapp.dto.user.UserRegistrationRequest;
import org.example.springbootapp.dto.user.UserResponseDto;
import org.example.springbootapp.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequest request) throws RegistrationException;
}
