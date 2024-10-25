package org.example.springbootapp.dto;

import org.example.springbootapp.model.Priority;

import java.time.LocalDateTime;

public record TodoCreateDto (
        String title,
        String description,
        LocalDateTime dueDate,
        Priority priority
) {
/**
 * -	title: String (required, limited to 100 characters, e.g., max length = 100)
 * -	description: String (optional, but if present, limited to 500 characters)
 * -	dueDate: LocalDateTime (required)
 * -	priority: enum (optional, can be values like LOW, MEDIUM, HIGH)
 */
}
