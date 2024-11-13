package org.example.springbootapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.springbootapp.TestcontainersConfiguration;
import org.example.springbootapp.dto.TodoResponseDto;
import org.example.springbootapp.model.Priority;
import org.example.springbootapp.model.Status;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {
        "classpath:database/users/remove-all-users.sql",
        "classpath:database/users/add-admin-user.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Import(TestcontainersConfiguration.class)
class TodoControllerIT {
    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired DataSource dataSource,
            @Autowired WebApplicationContext applicationContext
    ) throws SQLException {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        teardown(dataSource);
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/todos/add-three-default-todos.sql")
            );
        }
    }

    @AfterAll
    static void afterAll(
            @Autowired DataSource dataSource
    ) {
        teardown(dataSource);
    }

    @SneakyThrows
    static void teardown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/todos/remove-all-todos.sql")
            );
        }
    }

    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    @Test
    @DisplayName("Get all todos")
    void getAll_GivenTodosInCatalog_ShouldReturnAllTodos() throws Exception {
        // Given
        List<TodoResponseDto> expected = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 31, 23, 59, 59);
        expected.add(new TodoResponseDto(1L, "Task 1", "Description for Task 1", localDateTime, Priority.HIGH, Status.PENDING, localDateTime, localDateTime, 1L));
        expected.add(new TodoResponseDto(2L, "Task 2", "Description for Task 2", localDateTime, Priority.MEDIUM, Status.IN_PROGRESS, localDateTime, localDateTime, 1L));
        expected.add(new TodoResponseDto(3L, "Task 3", "Description for Task 3", localDateTime, Priority.LOW, Status.COMPLETED, localDateTime, localDateTime, 1L));

        // When
        MvcResult result = mockMvc.perform(get("/todos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        TodoResponseDto[] actual = objectMapper.readValue(result.getResponse().getContentAsByteArray(), TodoResponseDto[].class);
        Assertions.assertEquals(3, actual.length);
        Assertions.assertEquals(expected, Arrays.stream(actual).toList());
    }
}