package com.example.Gymify.controller;

import com.example.Gymify.model.dto.WorkoutDto;
import com.example.Gymify.service.WorkoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping
    public ResponseEntity<WorkoutDto> createWorkout(@RequestBody WorkoutDto workoutDto) {
        WorkoutDto savedWorkoutDto = workoutService.save(workoutDto);
        return ResponseEntity.ok(savedWorkoutDto);
    }

    @GetMapping
    public ResponseEntity<List<WorkoutDto>> getAllWorkouts() {
        List<WorkoutDto> allWorkoutsDto=workoutService.findAll();
        return ResponseEntity.ok(allWorkoutsDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<WorkoutDto>> getWorkoutById(@PathVariable Long id) {
        Optional<WorkoutDto> workoutByIdDto=workoutService.findById(id);
        return ResponseEntity.ok(workoutByIdDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        workoutService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
