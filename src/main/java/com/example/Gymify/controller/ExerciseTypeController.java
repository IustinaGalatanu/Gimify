package com.example.Gymify.controller;

import com.example.Gymify.model.ExerciseType;
import com.example.Gymify.repository.ExerciseTypeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/exercise-types")
public class ExerciseTypeController {

    private final ExerciseTypeRepository exerciseTypeRepository;

    public ExerciseTypeController(ExerciseTypeRepository exerciseTypeRepository) {
        this.exerciseTypeRepository = exerciseTypeRepository;
    }

    @GetMapping
    ResponseEntity<List<ExerciseType>> getAllExerciseTypes() {
        return ResponseEntity.ok(exerciseTypeRepository.findAll());
    }
}
