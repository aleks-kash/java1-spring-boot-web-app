package org.example.springbootapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "task_history")
public class TaskHistory {

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
