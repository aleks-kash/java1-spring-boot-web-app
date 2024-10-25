package org.example.springbootapp.dto;

import org.example.springbootapp.model.Priority;
import org.example.springbootapp.model.Status;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record TodoUpdateDto (

        @NotBlank(message = "Title can't be empty")
        @Length(max = 100, message = "Title length must not exceed 100 characters")
        String title,

        @Size(max = 500, message = "Description must be limited to 500 characters")
        String description,

        @NotNull(message = "Date can't be empty")
        @Future(message = "Due date cannot be in the past")
        LocalDateTime dueDate,

        Priority priority,

        @NotNull(message = "Status can't be null")
        Status status
) {
/**
 * -	title: String (required, limited to 100 characters, e.g., max length = 100)
 * -	description: String (optional, but if present, limited to 500 characters)
 * -	dueDate: LocalDateTime (required)
 * -	priority: enum (optional, values like LOW, MEDIUM, HIGH)
 * -	status: enum (required, values like PENDING, IN_PROGRESS, COMPLETED)
 */
}
