package com.example.Gymify;

import com.example.Gymify.model.ExerciseCatalog;
import com.example.Gymify.model.StrengthExercise;
import com.example.Gymify.model.Workout;
import com.example.Gymify.model.dto.ExerciseDto;
import com.example.Gymify.repository.ExerciseCatalogRepository;
import com.example.Gymify.repository.ExerciseRepository;
import com.example.Gymify.repository.WorkoutRepository;
import com.example.Gymify.service.implementation.ExerciseServiceImpl;
import com.example.Gymify.service.mapper.ExerciseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ExerciseServiceTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private ExerciseCatalogRepository exerciseCatalogRepository;

    @Mock
    private WorkoutRepository workoutRepository;

    @Mock
    private ExerciseMapper exerciseMapper;

    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    private ExerciseDto exerciseDto;
    private ExerciseCatalog catalog;
    private Workout workout;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        workout = new Workout();
        workout.setId(1L);
        workout.setName("Leg Day");

        catalog = new ExerciseCatalog();
        catalog.setId(1L);
        catalog.setName("Squat");
        catalog.setType("STRENGTH");

        exerciseDto = new ExerciseDto();
        exerciseDto.setCatalogId(1L);
        exerciseDto.setWorkoutId(1L);
        exerciseDto.setSets(4);
        exerciseDto.setReps(10);
        exerciseDto.setWeight(100.0);
    }

    @Test
    void save_ShouldReturnSavedExerciseDto() {
        StrengthExercise exercise = new StrengthExercise();
        exercise.setWorkout(workout);
        exercise.setExerciseCatalog(catalog);
        exercise.setSets(4);
        exercise.setReps(10);
        exercise.setWeight(100.0);

        when(workoutRepository.findById(1L)).thenReturn(Optional.of(workout));
        when(exerciseCatalogRepository.findById(1L)).thenReturn(Optional.of(catalog));
        when(exerciseMapper.createFromDto(exerciseDto, workout, catalog)).thenReturn(exercise);
        when(exerciseRepository.save(exercise)).thenReturn(exercise);
        when(exerciseMapper.toDto(exercise)).thenReturn(exerciseDto);

        ExerciseDto result = exerciseService.save(exerciseDto);

        assertThat(result).isNotNull();
        assertThat(result.getSets()).isEqualTo(4);
        assertThat(result.getWeight()).isEqualTo(100.0);
        verify(exerciseRepository, times(1)).save(exercise);
    }
}
