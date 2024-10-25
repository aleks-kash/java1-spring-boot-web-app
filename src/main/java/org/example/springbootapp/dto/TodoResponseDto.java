package org.example.springbootapp.dto;

import org.example.springbootapp.model.Priority;
import org.example.springbootapp.model.Status;

import java.time.LocalDateTime;

public class TodoResponseDto {
    /**
     * -	id: Long (unique identifier of the TODO item)
     * -	title: String (the title of the TODO item)
     * -	description: String (the description of the TODO item)
     * -	dueDate: LocalDateTime (the due date of the TODO item)
     * -	priority: enum (priority level of the TODO item, like LOW, MEDIUM, HIGH)
     * -	status: enum (current status of the TODO item, like PENDING, IN_PROGRESS, COMPLETED)
     * -	createdDate: LocalDateTime (timestamp when the TODO item was created)
     * -	updatedDate: LocalDateTime (timestamp when the TODO item was last updated)
     * -	userId: Long (the ID of the user who owns the TODO item)
     */

    Long id;
    String title;
    String description;
    LocalDateTime dueDate;
    Priority priority;
    Status status;
    LocalDateTime createdDate;
    LocalDateTime updatedDate;
    Long userId;
}
