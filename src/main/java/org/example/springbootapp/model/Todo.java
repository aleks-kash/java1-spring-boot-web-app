package org.example.springbootapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "todo")
@SQLDelete(sql = "UPDATE todo SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
public class Todo {

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

    Long userId = 1L;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskHistory> taskHistories = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isDeleted = false;
}
