package org.example.springbootapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "todo")
public class Todo {
    /**
     * -	id: Long (Unique identifier for the Todo item)
     * -	title: String (Short description or title of the task)
     * -	description: String (Detailed description of the task)
     * -	dueDate: LocalDateTime (When the task is due)
     * -	priority: Enum (e.g., LOW, MEDIUM, HIGH. Store enum value as varchar in DB
     * -	status: Enum (e.g., PENDING, IN_PROGRESS, COMPLETED)
     * -	createdDate: LocalDateTime (When the task was created)
     * -	updatedDate: LocalDateTime (When the task was last updated)
     * -	userId: Long (The ID of the user who owns the task). Hardcode value 1 for now. We will replace it when Security is implemented.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    String description;
    LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    Priority priority;

    @Enumerated(EnumType.STRING)
    Status status;
    LocalDateTime createdDate;
    LocalDateTime updatedDate;
    Long userIdn = 1L;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskHistory> taskHistories = new ArrayList<>();
}
