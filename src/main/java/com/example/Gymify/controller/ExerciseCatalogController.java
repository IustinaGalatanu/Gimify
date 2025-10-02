package com.example.Gymify.controller;

import com.example.Gymify.model.dto.ExerciseCatalogDto;
import com.example.Gymify.service.implementation.ExerciseCatalogServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/exercises-catalog")
@CrossOrigin("*")
public class ExerciseCatalogController {

    private final ExerciseCatalogServiceImpl exerciseCatalogService;

    public ExerciseCatalogController(ExerciseCatalogServiceImpl exerciseCatalogService) {
        this.exerciseCatalogService = exerciseCatalogService;
    }

    @GetMapping
    public ResponseEntity<List<ExerciseCatalogDto>> getAllExercise(){
        List<ExerciseCatalogDto> allExerciseToDto=exerciseCatalogService.findAll();
        return ResponseEntity.ok(allExerciseToDto);
    }
    @GetMapping("/by-type")
    public List<ExerciseCatalogDto> getByType(@RequestParam String type) {
        return exerciseCatalogService.findByType(type);
    }

}
