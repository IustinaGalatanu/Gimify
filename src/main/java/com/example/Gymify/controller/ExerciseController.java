package com.example.Gymify.controller;

import com.example.Gymify.model.dto.ExerciseDto;
import com.example.Gymify.service.implementation.ExerciseServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exercises")
@CrossOrigin("*")
public class ExerciseController {

    private final ExerciseServiceImpl exerciseService;

    public ExerciseController(ExerciseServiceImpl exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping
    public ResponseEntity<ExerciseDto> createExercise(@RequestBody ExerciseDto exerciseDto){
        ExerciseDto savedExerciseDto=exerciseService.save(exerciseDto);
        return ResponseEntity.ok(savedExerciseDto);
    }

    @GetMapping
    public ResponseEntity<List<ExerciseDto>> getAllExercise(){
        List<ExerciseDto> allExerciseToDto=exerciseService.findAll();
        return ResponseEntity.ok(allExerciseToDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ExerciseDto>> getExerciseById(@PathVariable Long id){
        Optional<ExerciseDto> exerciseByIdToDto=exerciseService.findById(id);
        return ResponseEntity.ok(exerciseByIdToDto);
    }

    @GetMapping("/workout/{id}")
    public ResponseEntity<List<ExerciseDto>> getExerciseByWorkoutId(@PathVariable Long id) {
        List<ExerciseDto> exerciseByWorkoutIdDto=exerciseService.findExerciseByWorkoutId(id);
        return ResponseEntity.ok(exerciseByWorkoutIdDto);
    }

    @PatchMapping("{id}/exercise-type")
    public ResponseEntity<ExerciseDto> updateExerciseType(@PathVariable Long id, @RequestParam Long catalogId) {
        ExerciseDto exerciseDtoUpdate = exerciseService.update(id,catalogId);
        return ResponseEntity.ok(exerciseDtoUpdate);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExerciseDto> updateExercise(@PathVariable Long id, @RequestBody ExerciseDto exerciseDto) {
        ExerciseDto exerciseDtoUpdate = exerciseService.update(id,exerciseDto);
        return ResponseEntity.ok(exerciseDtoUpdate);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        exerciseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
