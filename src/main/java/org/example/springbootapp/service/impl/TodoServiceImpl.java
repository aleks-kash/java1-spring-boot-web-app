package org.example.springbootapp.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.springbootapp.dto.TaskHistoryResponseDto;
import org.example.springbootapp.dto.TodoCreateDto;
import org.example.springbootapp.dto.TodoResponseDto;
import org.example.springbootapp.dto.TodoUpdateDto;
import org.example.springbootapp.exception.ResourceNotFoundException;
import org.example.springbootapp.mapper.TaskHistoryMapper;
import org.example.springbootapp.mapper.TodoMapper;
import org.example.springbootapp.model.Status;
import org.example.springbootapp.model.TaskHistory;
import org.example.springbootapp.model.Todo;
import org.example.springbootapp.model.User;
import org.example.springbootapp.repository.TaskHistoryRepository;
import org.example.springbootapp.repository.TodoRepository;
import org.example.springbootapp.repository.UserRepository;
import org.example.springbootapp.service.TodoService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;
    private final TaskHistoryRepository taskHistoryRepository;
    private final TaskHistoryMapper taskHistoryMapper;
    private final UserRepository userRepository;

    private Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            User user = (User) principal;
            return user.getId();
        } else {
            throw new IllegalStateException("Current User is not authenticated");
        }
    }

    private String getCurrentUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            User user = (User) principal;
            return user.getUsername();
        } else {
            throw new IllegalStateException("Current User is not authenticated");
        }
    }

    @Override
    public TodoResponseDto save(TodoCreateDto todoCreateDto) {
        Long userId = getCurrentUserId();

        Todo todo = todoMapper.toEntity(todoCreateDto);
        todo.setStatus(Status.PENDING);
        todo.setUserId(userId);
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
        updatedTodo.setUserId(getCurrentUserId());

        if (existingTodo.equals(updatedTodo)) {
            return todoMapper.toResponseDto(existingTodo);
        }

        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setTodo(existingTodo);
        taskHistory.setOldState(existingTodo.toString());

        Todo savedTodo = todoRepository.save(updatedTodo);

        taskHistory.setNewState(savedTodo.toString());
        taskHistory.setChangeDate(LocalDateTime.now());
        taskHistory.setChangedBy(getCurrentUserName());

        taskHistoryRepository.save(taskHistory);

        return todoMapper.toResponseDto(savedTodo);
    }

    @Override
    public void delete(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo with id " + id + " not found."));

        checkSoftDelete(todo, "Todo with id " + id + " is already deleted.");

        todo.setDeleted(true);
        todoRepository.save(todo);
    }

    @Override
    public List<TaskHistoryResponseDto> findTaskHistory(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo with id " + id + " not found."));

        checkSoftDelete(todo, "Cannot view history of a deleted Todo.");

        List<TaskHistory> historyList = taskHistoryRepository.findByTodoId(id);

        return historyList.stream()
                .map(taskHistoryMapper::toResponseDto)
                .collect(Collectors.toList());
    }

//    @Override
//    public List<TodoResponseDto> findAllTodo() {
//        return todoRepository.findAll().stream()
//                .map(todoMapper::toResponseDto)
//                .toList();
//    }

    @Override
    public List<TodoResponseDto> findAll(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email " + email + " not found."));
        return todoRepository.findAllByUserId(user.getId()).stream()
                .map(todoMapper::toResponseDto)
                .toList();
    }

    private void checkSoftDelete(Todo todo, String errorMessage) {
        if (todo.isDeleted()) {
            throw new IllegalStateException(errorMessage);
        }
    }
}
