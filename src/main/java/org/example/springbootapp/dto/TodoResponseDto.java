package org.example.springbootapp.dto;

import org.example.springbootapp.model.Priority;
import org.example.springbootapp.model.Status;

import java.time.LocalDateTime;

public record TodoResponseDto (
        Long id,
        String title,
        String description,
        LocalDateTime dueDate,
        Priority priority,
        Status status,
        LocalDateTime createdDate,
        LocalDateTime updatedDate,
        Long userId
) {

}
