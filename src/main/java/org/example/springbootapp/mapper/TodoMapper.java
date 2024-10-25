package org.example.springbootapp.mapper;

import org.example.springbootapp.dto.TodoCreateDto;
import org.example.springbootapp.dto.TodoResponseDto;
import org.example.springbootapp.dto.TodoUpdateDto;
import org.example.springbootapp.model.Todo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    Todo toEntity(TodoCreateDto todoCreateDto);

    Todo toEntity(TodoUpdateDto todoUpdateDto);

    TodoResponseDto toResponseDto(Todo todo);
}
