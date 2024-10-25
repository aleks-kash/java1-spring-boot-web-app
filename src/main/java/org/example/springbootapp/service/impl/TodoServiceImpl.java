package org.example.springbootapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.springbootapp.dto.TaskHistoryResponseDto;
import org.example.springbootapp.dto.TodoCreateDto;
import org.example.springbootapp.dto.TodoResponseDto;
import org.example.springbootapp.dto.TodoUpdateDto;
import org.example.springbootapp.service.TodoService;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {

    @Override
    public TodoResponseDto save(TodoCreateDto todoCreateDto) {
        return null;
    }

    @Override
    public TodoResponseDto update(Long id, TodoUpdateDto todoUpdateDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<TaskHistoryResponseDto> findTaskHistory(Long id) {
        return List.of();
    }
}
