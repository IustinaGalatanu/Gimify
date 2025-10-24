package com.example.Gymify.it;

import com.example.Gymify.it.util.JsonMapper;
import com.example.Gymify.model.ExerciseCatalog;
import com.example.Gymify.model.StrengthExercise;
import com.example.Gymify.model.Workout;
import com.example.Gymify.model.dto.ExerciseDto;
import com.example.Gymify.repository.ExerciseCatalogRepository;
import com.example.Gymify.repository.ExerciseRepository;
import com.example.Gymify.repository.WorkoutRepository;
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


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")

public class ExerciseIntegrationTest {

    private final ExerciseRepository exerciseRepository;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final ExerciseCatalogRepository exerciseCatalogRepository;
    private final WorkoutRepository workoutRepository;

    @Autowired
    public ExerciseIntegrationTest(ExerciseRepository exerciseRepository, MockMvc mockMvc, ObjectMapper objectMapper, ExerciseMapper exerciseMapper, ExerciseCatalogRepository exerciseCatalogRepository, WorkoutRepository workoutRepository) {
        this.exerciseRepository = exerciseRepository;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.exerciseCatalogRepository = exerciseCatalogRepository;
        this.workoutRepository = workoutRepository;

    }

    private ExerciseCatalog catalog;
    private Workout workout;
    private ExerciseDto exerciseDto;


    @BeforeEach
    void setUp() {
        exerciseRepository.deleteAll();
        exerciseCatalogRepository.deleteAll();
        workoutRepository.deleteAll();

        workout= new Workout();
        workout.setName("Leg Day");
        workoutRepository.save(workout);

        catalog=new ExerciseCatalog();
        catalog.setName("Squat");
        catalog.setType("STRENGTH");
        exerciseCatalogRepository.save(catalog);

        exerciseDto=new ExerciseDto();
        exerciseDto.setCatalogId(catalog.getId());
        exerciseDto.setWorkoutId(workout.getId());
        exerciseDto.setSets(4);
        exerciseDto.setReps(12);
        exerciseDto.setWeight(160.00);
    }

    @Test
    @org.springframework.security.test.context.support.WithMockUser
    void createExercise_ShouldReturnCreatedExercise() throws  Exception{
        ResultActions resultActions= mockMvc.perform(post("/api/exercises")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.classToJson(exerciseDto)))
                .andExpect(status().isOk());

        String responseBody = resultActions.andReturn()
                .getResponse()
                .getContentAsString();
        ExerciseDto resultExerciseDto = JsonMapper.jsonToClass(responseBody,ExerciseDto.class);
        assertEquals(exerciseDto.getCatalogId(), resultExerciseDto.getCatalogId());
        assertEquals(exerciseDto.getSets(), resultExerciseDto.getSets());
        assertEquals(exerciseDto.getReps(), resultExerciseDto.getReps());
        assertEquals(exerciseDto.getWeight(), resultExerciseDto.getWeight());

    }

    @Test
    @org.springframework.security.test.context.support.WithMockUser
    void getAllExercises_ShouldReturnList() throws Exception {
        catalog = new ExerciseCatalog();
        catalog.setName("Bench Press");
        catalog.setType("STRENGTH");
        catalog = exerciseCatalogRepository.save(catalog);

        workout = new Workout();
        workout.setName("Chest Day");
        workout = workoutRepository.save(workout);

        StrengthExercise exercise = new StrengthExercise();
        exercise.setExerciseCatalog(catalog);
        exercise.setWorkout(workout);
        exercise.setSets(4);
        exercise.setReps(10);
        exercise.setWeight(180.0);
        exerciseRepository.saveAndFlush(exercise);

        mockMvc.perform(get("/api/exercises/workout/{id}", workout.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].catalogId").value(catalog.getId()))
                .andExpect(jsonPath("$[0].sets").value(4))
                .andExpect(jsonPath("$[0].reps").value(10))
                .andExpect(jsonPath("$[0].weight").value(180.0));

    }

    @Test
    @org.springframework.security.test.context.support.WithMockUser
    void getExercisesByWorkoutId_ShouldReturnList_WhenExists() throws Exception{
        catalog = new ExerciseCatalog();
        catalog.setName("Bench Press");
        catalog.setType("STRENGTH");
        catalog = exerciseCatalogRepository.save(catalog);

        workout = new Workout();
        workout.setName("Chest Day");
        workout = workoutRepository.save(workout);

        StrengthExercise exercise = new StrengthExercise();
        exercise.setExerciseCatalog(catalog);
        exercise.setWorkout(workout);
        exercise.setSets(4);
        exercise.setReps(12);
        exercise.setWeight(160.0);
        exerciseRepository.saveAndFlush(exercise);

        ResultActions result = mockMvc.perform(get("/api/exercises/workout/{id}", workout.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].catalogId").value(this.catalog.getId()))
                .andExpect(jsonPath("$[0].sets").value(4))
                .andExpect(jsonPath("$[0].reps").value(12))
                .andExpect(jsonPath("$[0].weight").value(160.0));
        String response = result.andReturn().getResponse().getContentAsString();
        System.out.println("Response JSON: " + response);

    }

    @Test
    @org.springframework.security.test.context.support.WithMockUser
    void updateExercise_ShouldReturnUpdatedExercise() throws Exception {
        StrengthExercise exercise = new StrengthExercise();
        exercise.setWorkout(workout);
        exercise.setExerciseCatalog(catalog);
        exercise.setSets(4);
        exercise.setReps(12);
        exercise.setWeight(150.0);
        exercise = exerciseRepository.saveAndFlush(exercise);

        ExerciseDto updateDto = new ExerciseDto();
        updateDto.setSets(6);
        updateDto.setReps(10);
        updateDto.setWeight(180.0);

        mockMvc.perform(patch("/api/exercises/{id}", exercise.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.classToJson(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sets").value(6))
                .andExpect(jsonPath("$.reps").value(10))
                .andExpect(jsonPath("$.weight").value(180.0));
    }
    @Test
    @org.springframework.security.test.context.support.WithMockUser
    void deleteExercise_ShouldRemoveEntity() throws Exception {
        StrengthExercise exercise = new StrengthExercise();
        exercise.setWorkout(workout);
        exercise.setExerciseCatalog(catalog);
        exercise.setSets(3);
        exercise.setReps(15);
        exercise.setWeight(120.0);
        exercise = exerciseRepository.saveAndFlush(exercise);

        mockMvc.perform(delete("/api/exercises/{id}", exercise.getId()))
                .andExpect(status().isNoContent());

        assertThat(exerciseRepository.findById(exercise.getId())).isEmpty();
    }


}
