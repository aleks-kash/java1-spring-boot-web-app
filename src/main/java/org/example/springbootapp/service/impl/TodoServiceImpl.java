package org.example.springbootapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.springbootapp.dto.TaskHistoryResponseDto;
import org.example.springbootapp.dto.TodoCreateDto;
import org.example.springbootapp.dto.TodoResponseDto;
import org.example.springbootapp.dto.TodoUpdateDto;
import org.example.springbootapp.mapper.TodoMapper;
import org.example.springbootapp.model.Status;
import org.example.springbootapp.model.Todo;
import org.example.springbootapp.repository.TodoRepository;
import org.example.springbootapp.service.TodoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    @Override
    public TodoResponseDto save(TodoCreateDto todoCreateDto) {
        Todo todo = todoMapper.toEntity(todoCreateDto);
        todo.setStatus(Status.PENDING);
        todo.setUserId(1L);
        todo.setCreatedDate(LocalDateTime.now());
        todo.setUpdatedDate(LocalDateTime.now());
        return todoMapper.toResponseDto(todoRepository.save(todo));
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
