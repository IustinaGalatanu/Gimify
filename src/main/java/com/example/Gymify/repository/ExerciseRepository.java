package com.example.Gymify.repository;

import com.example.Gymify.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise,Long> {
    List<Exercise> findExerciseByWorkoutId(Long id);

}
