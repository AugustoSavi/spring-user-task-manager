package com.spring.todolist.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.todolist.IntegrationTest;
import com.spring.todolist.user.User;
import com.spring.todolist.user.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser() throws Exception {
        User user = createUserAndNotSave();

        mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(user))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.username").value(user.getUsername()));

        deleteAllUsers();
    }

    @Test
    void getUsers() throws Exception {
        User user = createUserAndSave();
        User user1 = createUserAndSave();

        iUserRepository.save(user);
        iUserRepository.save(user1);

        mockMvc.perform(
                        get("/users")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        deleteAllUsers();
    }


    User createUserAndSave() {
        User user = new User(UUID.randomUUID().toString(), "name", "password");
        return iUserRepository.save(user);
    }

    User createUserAndNotSave() {
        return new User(UUID.randomUUID().toString(), "name", "password");
    }

    void deleteAllUsers() {
        iUserRepository.deleteAll();
    }
}