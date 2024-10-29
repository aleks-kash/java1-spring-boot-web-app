package org.example.springbootapp.mapper;

import org.example.springbootapp.dto.user.UserResponseDto;
import org.example.springbootapp.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toUserResponse(User user);
}
