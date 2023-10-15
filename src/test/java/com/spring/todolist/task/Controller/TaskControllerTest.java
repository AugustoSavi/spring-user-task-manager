package com.spring.todolist.task.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.todolist.IntegrationTest;
import com.spring.todolist.task.Task;
import com.spring.todolist.task.repository.ITaskRepository;
import com.spring.todolist.user.User;
import com.spring.todolist.user.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IntegrationTest
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private ITaskRepository iTaskRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static String getAuthUser(User user) {
        return "Basic " + Base64.getEncoder().encodeToString((user.getUsername() + ":password").getBytes());
    }

    @Test
    void create() throws Exception {
        Task task = new Task("description", "title", LocalDateTime.MIN, LocalDateTime.MAX, "ALTA");
        User user = createUserAndSave();

        mockMvc.perform(
                        post("/tasks")
                                .with(request -> {
                                    request.setAttribute("idUsuario", user.getId());
                                    return request;
                                })
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(task))
                                .header(HttpHeaders.AUTHORIZATION, getAuthUser(user))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.description").value(task.getDescription()))
                .andExpect(jsonPath("$.title").value(task.getTitle()))
                .andDo(MockMvcResultHandlers.print());

        deleteAllUsers();
        deleteAllTasks();
    }

    @Test
    void readAll() throws Exception {
        User user = createUserAndSave();
        Task task = new Task("description", "title", LocalDateTime.MIN, LocalDateTime.MAX, "ALTA");
        iTaskRepository.save(task);

        task.setIdUsuario(user.getId());
        iTaskRepository.save(task);

        Task task1 = new Task("description", "title", LocalDateTime.MIN, LocalDateTime.MAX, "ALTA");
        task1.setIdUsuario(user.getId());
        iTaskRepository.save(task1);

        mockMvc.perform(
                        get("/tasks")
                                .with(request -> {
                                    request.setAttribute("idUsuario", user.getId());
                                    return request;
                                })
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, getAuthUser(user))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(MockMvcResultHandlers.print());

        deleteAllUsers();
        deleteAllTasks();
    }

    @Test
    void readOne() throws Exception {
        User user = createUserAndSave();
        Task task = new Task("description1", "title", LocalDateTime.MIN, LocalDateTime.MAX, "ALTA");
        task.setIdUsuario(user.getId());
        Task saved = iTaskRepository.save(task);

        mockMvc.perform(
                        get("/tasks/" + saved.getId())
                                .with(request -> {
                                    request.setAttribute("idUsuario", user.getId());
                                    return request;
                                })
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId().toString()))
                .andExpect(jsonPath("$.description").value(task.getDescription()))
                .andExpect(jsonPath("$.title").value(task.getTitle()))
                .andDo(MockMvcResultHandlers.print());

        deleteAllUsers();
        deleteAllTasks();
    }

    @Test
    void update() throws Exception {
        User user = createUserAndSave();
        Task task = new Task("description1", "title", LocalDateTime.MIN, LocalDateTime.MAX, "ALTA");
        task.setIdUsuario(user.getId());
        Task saved = iTaskRepository.save(task);
        task.setDescription("description2");

        mockMvc.perform(
                        put("/tasks/" + saved.getId())
                                .with(request -> {
                                    request.setAttribute("idUsuario", user.getId());
                                    return request;
                                })
                                .content(objectMapper.writeValueAsString(task))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId().toString()))
                .andExpect(jsonPath("$.description").value(task.getDescription()))
                .andExpect(jsonPath("$.title").value(task.getTitle()))
                .andDo(MockMvcResultHandlers.print());

        deleteAllUsers();
        deleteAllTasks();
    }

    @Test
    void deleteTask() throws Exception {
        User user = createUserAndSave();
        Task task = new Task("description1", "title", LocalDateTime.MIN, LocalDateTime.MAX, "ALTA");
        task.setIdUsuario(user.getId());
        Task saved = iTaskRepository.save(task);

        mockMvc.perform(
                        delete("/tasks/" + saved.getId())
                                .with(request -> {
                                    request.setAttribute("idUsuario", user.getId());
                                    return request;
                                })
                ).andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string("registro deletado"));

        deleteAllUsers();
        deleteAllTasks();
    }


    User createUserAndSave() {
        User user = new User(UUID.randomUUID().toString(), "name", "password");
        return iUserRepository.save(user);
    }

    void deleteAllUsers() {
        iUserRepository.deleteAll();
    }

    void deleteAllTasks() {
        iTaskRepository.deleteAll();
    }
}