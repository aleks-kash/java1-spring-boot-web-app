package org.example.springbootapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springbootapp.dto.TaskHistoryResponseDto;
import org.example.springbootapp.dto.TodoCreateDto;
import org.example.springbootapp.dto.TodoResponseDto;
import org.example.springbootapp.dto.TodoUpdateDto;
import org.example.springbootapp.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/todos")
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public TodoResponseDto save(@Valid @RequestBody TodoCreateDto todoCreateDto) {
        return todoService.save(todoCreateDto);
    }

    @PutMapping("/{id}")
    public TodoResponseDto updateTodo(@PathVariable Long id, @Valid @RequestBody TodoUpdateDto todoUpdateDto) {
        return todoService.update(id, todoUpdateDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/history")
    public List<TaskHistoryResponseDto> getTaskHistory(@PathVariable Long id) {
        return todoService.findTaskHistory(id);
    }
}
