package com.example.Gymify.it;

import com.example.Gymify.it.util.JsonMapper;
import com.example.Gymify.model.Exercise;
import com.example.Gymify.model.dto.ExerciseDto;
import com.example.Gymify.repository.ExerciseRepository;
import com.example.Gymify.service.mapper.ExerciseMapper;
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

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ExerciseIntegrationTest {

    private final ExerciseRepository exerciseRepository;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final ExerciseMapper exerciseMapper;

    @Autowired
    public ExerciseIntegrationTest(ExerciseRepository exerciseRepository, MockMvc mockMvc, ObjectMapper objectMapper, ExerciseMapper exerciseMapper) {
        this.exerciseRepository = exerciseRepository;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.exerciseMapper = exerciseMapper;
    }

    private ExerciseDto exerciseDto=createExercise();

    private ExerciseDto createExercise() {
        exerciseDto=new ExerciseDto();
        exerciseDto.setName("Hipthrust");
        exerciseDto.setSets(4);
        exerciseDto.setRep(12);
        exerciseDto.setWeight(160);
        return exerciseDto;
    }

    @BeforeEach
    void setUp() {
        exerciseRepository.deleteAll();
    }

    @Test
    void createExercise_ShouldReturnCreatedExercise() throws  Exception{
        ResultActions resultActions= mockMvc.perform(post("/api/exercises")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.classToJson(exerciseDto)));
        String responseBody = resultActions.andReturn()
                .getResponse()
                .getContentAsString();
        ExerciseDto resultExerciseDto = JsonMapper.jsonToClass(responseBody,ExerciseDto.class);
        ExerciseDto expectedExerciseDto=new ExerciseDto();
        expectedExerciseDto.setId(1L);
        expectedExerciseDto.setName("Hipthrust");
        expectedExerciseDto.setSets(4);
        expectedExerciseDto.setRep(12);
        expectedExerciseDto.setWeight(160);
        assertEquals(expectedExerciseDto, resultExerciseDto);
    }

    @Test
    void getAllExercises_ShouldReturnList() throws Exception {
        Exercise exercise=new Exercise();
        exercise.setName("Hipthrust");
        exercise.setSets(4);
        exercise.setRep(12);
        exercise.setWeight(160);
        exerciseRepository.saveAndFlush(exercise);
        ResultActions resultActions=mockMvc.perform(get("/api/exercises")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exercise)));
    }




}
