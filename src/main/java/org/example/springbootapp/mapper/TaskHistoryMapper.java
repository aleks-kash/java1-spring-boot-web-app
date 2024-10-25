package org.example.springbootapp.mapper;

import org.example.springbootapp.dto.TaskHistoryResponseDto;
import org.example.springbootapp.model.TaskHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskHistoryMapper {

    @Mapping(target = "todoId", source = "todo.id")
    TaskHistoryResponseDto toResponseDto(TaskHistory taskHistory);
}
