package com.example.Gymify.controller;

import com.example.Gymify.model.dto.ExerciseCatalogDto;
import com.example.Gymify.service.implementation.ExerciseCatalogServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/exercises-catalog")
@CrossOrigin("*")
@SecurityRequirement(name="BearerAuth")
public class ExerciseCatalogController {

    private final ExerciseCatalogServiceImpl exerciseCatalogService;

    public ExerciseCatalogController(ExerciseCatalogServiceImpl exerciseCatalogService) {
        this.exerciseCatalogService = exerciseCatalogService;
    }
    @Operation(summary = "Get all exercises from catalog")
    @GetMapping
    public ResponseEntity<List<ExerciseCatalogDto>> getAllExercise(){
        List<ExerciseCatalogDto> allExerciseToDto=exerciseCatalogService.findAll();
        return ResponseEntity.ok(allExerciseToDto);
    }
    @Operation(summary = "Get all exercises by type")
    @GetMapping("/by-type")
    public List<ExerciseCatalogDto> getByType(@RequestParam String type) {
        return exerciseCatalogService.findByType(type);
    }

}
