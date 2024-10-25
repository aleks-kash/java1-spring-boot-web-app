package org.example.springbootapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "task_history")
public class TaskHistory {
    /**
     * -	id: Long (Unique identifier for the TaskHistory entry)
     * -	todo: Todo (reference to related Todo item)
     * -	oldState: String (to simplify the task you can use Todo toString() method response and store here. For advanced solution - convert Todo entity to JSON format and store it)
     * -	newState: String (same as for oldState)
     * -	changeDate: LocalDateTime (The timestamp when the change was made)
     * -	changedBy: String (The name or ID of the user who made the change). Feel free to leave it null for now.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "todo_id", nullable = false)
    Todo todo;
    String oldState;
    String newState;
    LocalDateTime changeDate;
    String changedBy;
}
