package com.example.Gymify.controller;

import com.example.Gymify.model.ExerciseType;
import com.example.Gymify.model.dto.ExerciseTypeDto;
import com.example.Gymify.repository.ExerciseTypeRepository;
import com.example.Gymify.service.ExerciseTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/exercise-types")
@CrossOrigin("*")
public class ExerciseTypeController {

    private final ExerciseTypeService exerciseTypeService;

    public ExerciseTypeController( ExerciseTypeService exerciseTypeService) {
        this.exerciseTypeService = exerciseTypeService;
    }

    @GetMapping
    ResponseEntity<List<ExerciseTypeDto>> getAllExerciseTypes() {
        return ResponseEntity.ok(exerciseTypeService.findAll());
    }
}
