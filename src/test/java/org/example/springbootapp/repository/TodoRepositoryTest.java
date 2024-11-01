package org.example.springbootapp.repository;

import org.example.springbootapp.TestcontainersConfiguration;
import org.example.springbootapp.model.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
public class TodoRepositoryTest {
    @Autowired
    private TodoRepository todoRepository;

    @Test
    @DisplayName("Find all todos by user ID with pagination")
    @Sql(scripts = "classpath:database/todos/add-todos-for-user.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/todos/remove-todos-for-user.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByUserId_UserHasMultipleTodos_ReturnsTodos() {
        Long userId = 4L;
        Pageable pageable = PageRequest.of(0, 2);

        Page<Todo> page = todoRepository.findAllByUserId(userId, pageable);

        assertEquals(2, page.getContent().size());
        assertEquals(3, page.getTotalElements());
        assertEquals("Task 1", page.getContent().get(0).getTitle());
    }
}
