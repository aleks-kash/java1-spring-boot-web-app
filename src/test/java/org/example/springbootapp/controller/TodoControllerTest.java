package org.example.springbootapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springbootapp.model.Priority;
import org.example.springbootapp.model.Status;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.example.springbootapp.service.TodoService;
import org.example.springbootapp.security.JwtUtil;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.springbootapp.dto.TodoCreateDto;
import org.example.springbootapp.dto.TodoResponseDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(controllers = TodoController.class, excludeAutoConfiguration = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class
})
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TodoService todoService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    void createTodo_ValidRequestDto_Success() throws Exception {
        TodoCreateDto requestDto = new TodoCreateDto(
                "Test Todo", "Description of the todo", LocalDateTime.of(2024, 12, 31, 23, 59, 59), Priority.HIGH
        );

        TodoResponseDto responseDto = new TodoResponseDto(
                1L, "Test Todo", "Description of the todo",
                LocalDateTime.of(2024, 12, 31, 23, 59, 59), Priority.HIGH, Status.PENDING, LocalDateTime.now(), LocalDateTime.now(), 1L);

        Mockito.when(todoService.save(any(TodoCreateDto.class))).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(responseDto.id()))
                .andExpect(jsonPath("$.title").value(responseDto.title()))
                .andExpect(jsonPath("$.description").value(responseDto.description()));
    }

    @Test
    void createTodo_InvalidRequestDto_ShouldReturnBadRequest() throws Exception {
        TodoCreateDto invalidRequest = new TodoCreateDto("", "Description with error", LocalDateTime.now().minusDays(1), Priority.HIGH);

        mockMvc.perform(MockMvcRequestBuilders.post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists());
    }

    @Test
    void getAllTodos_ShouldReturnTodos() throws Exception {
        String email = "test@example.com";
        TodoResponseDto todo1 = new TodoResponseDto(1L, "Test Todo 1", "Description for todo 1", LocalDateTime.now(), Priority.HIGH, Status.PENDING, LocalDateTime.now(),LocalDateTime.now(),10L);
        TodoResponseDto todo2 = new TodoResponseDto(2L, "Test Todo 2", "Description for todo 2", LocalDateTime.now(), Priority.MEDIUM, Status.IN_PROGRESS, LocalDateTime.now(),LocalDateTime.now(), 10L);

        Authentication mockAuthentication = Mockito.mock(Authentication.class);
        Mockito.when(mockAuthentication.getName()).thenReturn(email);

        Mockito.when(todoService.findAll(eq(email), any(Pageable.class))).thenReturn(List.of(todo1, todo2));

        mockMvc.perform(MockMvcRequestBuilders.get("/todos")
                        .principal(mockAuthentication)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(todo1.id()))
                .andExpect(jsonPath("$[0].title").value(todo1.title()))
                .andExpect(jsonPath("$[1].id").value(todo2.id()))
                .andExpect(jsonPath("$[1].title").value(todo2.title()));
    }
}
