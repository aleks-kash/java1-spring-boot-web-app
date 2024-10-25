package org.example.springbootapp.dto;

import java.time.LocalDateTime;

public class TaskHistoryResponseDto {
    /**
     * -	id: Long (unique identifier of the task history entry)
     * -	todoId: Long (ID of the related TODO item)
     * -	oldState: String (state before the change)
     * -	newState: String (state after the change)
     * -	changeDate: LocalDateTime (timestamp when the change was made)
     * -	changedBy: String (the user who made the change)
     */

    Long id;
    Long todoId;
    String oldState;
    String newState;
    LocalDateTime changeDate;
    String changedBy;
}
