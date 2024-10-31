package org.example.springbootapp.repository;

import org.example.springbootapp.model.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Page<Todo> findAllByUserId(Long userId, Pageable pageable);
}
