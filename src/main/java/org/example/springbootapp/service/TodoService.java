package org.example.springbootapp.service;

import org.example.springbootapp.dto.TaskHistoryResponseDto;
import org.example.springbootapp.dto.TodoCreateDto;
import org.example.springbootapp.dto.TodoResponseDto;
import org.example.springbootapp.dto.TodoUpdateDto;

import java.util.List;

public interface TodoService {
    TodoResponseDto save(TodoCreateDto todoCreateDto);

    TodoResponseDto update(Long id, TodoUpdateDto todoUpdateDto);

    void delete(Long id);

    List<TaskHistoryResponseDto> findTaskHistory(Long id);
}
