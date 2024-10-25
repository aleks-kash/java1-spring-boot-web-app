package org.example.springbootapp.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.springbootapp.dto.TaskHistoryResponseDto;
import org.example.springbootapp.dto.TodoCreateDto;
import org.example.springbootapp.dto.TodoResponseDto;
import org.example.springbootapp.dto.TodoUpdateDto;
import org.example.springbootapp.exception.ResourceNotFoundException;
import org.example.springbootapp.mapper.TodoMapper;
import org.example.springbootapp.model.Status;
import org.example.springbootapp.model.TaskHistory;
import org.example.springbootapp.model.Todo;
import org.example.springbootapp.repository.TaskHistoryRepository;
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
    private final TaskHistoryRepository taskHistoryRepository;

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
    @Transactional
    public TodoResponseDto update(Long id, TodoUpdateDto todoUpdateDto) {
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo with id " + id + " not found."));

        Todo updatedTodo = todoMapper.toEntity(todoUpdateDto);
        updatedTodo.setId(id);
        updatedTodo.setCreatedDate(existingTodo.getCreatedDate());
        updatedTodo.setUpdatedDate(LocalDateTime.now());

        if (existingTodo.equals(updatedTodo)) {
            return todoMapper.toResponseDto(existingTodo);
        }

        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setTodo(existingTodo);
        taskHistory.setOldState(existingTodo.toString());

        Todo savedTodo = todoRepository.save(updatedTodo);

        taskHistory.setNewState(savedTodo.toString());
        taskHistory.setChangeDate(LocalDateTime.now());
        taskHistoryRepository.save(taskHistory);

        return todoMapper.toResponseDto(savedTodo);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<TaskHistoryResponseDto> findTaskHistory(Long id) {
        return List.of();
    }
}
