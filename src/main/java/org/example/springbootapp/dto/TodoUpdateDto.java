package org.example.springbootapp.dto;

import org.example.springbootapp.model.Priority;
import org.example.springbootapp.model.Status;

import java.time.LocalDateTime;

public class TodoUpdateDto {
    /**
     * -	title: String (required, limited to 100 characters, e.g., max length = 100)
     * -	description: String (optional, but if present, limited to 500 characters)
     * -	dueDate: LocalDateTime (required)
     * -	priority: enum (optional, values like LOW, MEDIUM, HIGH)
     * -	status: enum (required, values like PENDING, IN_PROGRESS, COMPLETED)
     */

    String title;
    String description;
    LocalDateTime dueDate;
    Priority priority;
    Status status;
}
