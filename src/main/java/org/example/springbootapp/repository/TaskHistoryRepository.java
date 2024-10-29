package org.example.springbootapp.repository;

import org.example.springbootapp.model.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {
    List<TaskHistory> findByTodoId(Long todoId);
}
