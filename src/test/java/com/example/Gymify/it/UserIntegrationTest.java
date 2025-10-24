package com.example.Gymify.it;

import com.example.Gymify.model.User;
import com.example.Gymify.model.dto.UserDto;
import com.example.Gymify.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserIntegrationTest {

    private final UserRepository userRepository;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserIntegrationTest(UserRepository userRepository, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    private UserDto userDto;

    @BeforeEach
    void setUp(){
        userRepository.deleteAll();
        userDto = new UserDto();
        userDto.setName("Iustina");
        userDto.setEmail("iustinagalatanu@gmail.com");
        userDto.setPassword("1234");
        userDto.setGoal("Strength");
    }

    @Test
    @WithMockUser
    void createUser_ShouldReturnCreatedUser() throws Exception {
        ResultActions resultActions =mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id",notNullValue()))
                .andExpect(jsonPath("$.name",is("Iustina")))
                .andExpect(jsonPath("$.email",is("iustinagalatanu@gmail.com")))
                //.andExpect(jsonPath("$.password",is("1234")))
                .andExpect(jsonPath("$.goal",is("Strength")));
    }

    @Test
    @WithMockUser
    void getAllUsers_ShouldReturnList() throws Exception {
        User user= new User();
        user.setName("Iustina");
        user.setEmail("iustinagalatanu@gmail.com");
        user.setGoal("Strength");
        userRepository.saveAndFlush(user);

        ResultActions resultActions=mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$",isA(List.class)))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id",notNullValue()))
                .andExpect(jsonPath("$[0].name",is("Iustina")))
                .andExpect(jsonPath("$[0].email",is("iustinagalatanu@gmail.com")))
                .andExpect(jsonPath("$[0].goal",is("Strength")));
    }

    @Test
    @WithMockUser
    void getAllUsers_ShouldReturnEmptyList_WhenDoesNOtExists() throws Exception {
        ResultActions resultActions=mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$", isA(List.class)))
                .andExpect(jsonPath("$.length()").value((0)));
    }

    @Test
    @WithMockUser
    void getUserById_ShouldReturnUser_WhenExists() throws Exception {
        String response=mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andReturn().getResponse().getContentAsString();
        UserDto savedUserDto=objectMapper.readValue(response, UserDto.class);
        ResultActions resultActions=mockMvc.perform(get("/api/users/"+savedUserDto.getId()));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Iustina")))
                .andExpect(jsonPath("$.email",is("iustinagalatanu@gmail.com")))
                .andExpect(jsonPath("$.goal",is("Strength")));
    }

    @Test
    @WithMockUser
    void deleteUserById_ShouldReturnNoContent() throws Exception{
        String response = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andReturn().getResponse().getContentAsString();
        UserDto savedUserDto = objectMapper.readValue(response, UserDto.class);
        ResultActions resultActions=mockMvc.perform(delete("/api/users/"+savedUserDto.getId()));
        resultActions.andExpect(status().isNoContent());
    }

}
