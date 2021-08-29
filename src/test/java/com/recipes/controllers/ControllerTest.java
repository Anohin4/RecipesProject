package com.recipes.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipes.model.Recipe;
import com.recipes.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    Controller controller;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createUser() throws Exception{
        User user = new User("TestUser11", "12345678");
        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username="user1", password = "12345678")
    void addRecipe() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setName("Tea");
        recipe.setCategory("tea");
        recipe.setDescription("It's just a tea");
        recipe.setDirections(List.of("postav chainik", "make some tea"));
        recipe.setIngredients(List.of("Water", "tea"));

        mockMvc.perform(post("/api/recipe/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(recipe)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="user1", password = "12345678")
    void getRecipe() throws Exception{
        int id = 1;
        mockMvc.perform(get("/api/recipe/" + id))
                .andDo(print())
                .andExpect(status().isOk());
    }



}