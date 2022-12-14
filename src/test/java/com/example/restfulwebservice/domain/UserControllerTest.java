package com.example.restfulwebservice.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @MockBean
    UserDaoService userService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("전체 유저 조회")
    void retrieveUsers() throws Exception{
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("유저 하나 조회")
    void retrieveUser() throws Exception{
        mockMvc.perform(get("/users/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("createdAt").exists())
        ;
    }

    @Test
    @DisplayName("유저 조회 실패")
    void retrieveUserNotFound() throws Exception{
        mockMvc.perform(get("/users/{id}", 111))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    @DisplayName("유저 등록")
    void registerUser() throws Exception{
        User user = new User("TestUser", LocalDateTime.now());
        when(userService.save(user)).thenReturn(user);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isCreated())
        ;
    }

    @Test
    @DisplayName("유저 삭제")
    void deleteUser() throws Exception{
        User user = new User("TestUser", LocalDateTime.now());

        mockMvc.perform(delete("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @DisplayName("선택된 유저 삭제 실패")
    void deleteUserNotFound() throws Exception{
        User user = new User("TestUser", LocalDateTime.now());

        mockMvc.perform(delete("/users/{id}", 100)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }




}