package org.example.springbootapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.springbootapp.dto.user.UserRegistrationRequestDto;
import org.example.springbootapp.dto.user.UserResponseDto;
import org.example.springbootapp.exception.RegistrationException;
import org.example.springbootapp.mapper.UserMapper;
import org.example.springbootapp.model.User;
import org.example.springbootapp.repository.UserRepository;
import org.example.springbootapp.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException {
        if(userRepository.findByEmail(request.email()).isPresent()) {
            throw new RegistrationException("Unable to complete registration!");
        }

        User user = new User();
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEmail(request.email());

        User userSaved = userRepository.save(user);
        return userMapper.toUserResponse(userSaved);
    }
}
