package com.example.Gymify.controller;

import com.example.Gymify.model.dto.WorkoutDto;
import com.example.Gymify.model.dto.WorkoutSummaryDto;
import com.example.Gymify.service.implementation.WorkoutServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/workouts")
@CrossOrigin("*")
public class WorkoutController {

    private final WorkoutServiceImpl workoutService;

    public WorkoutController(WorkoutServiceImpl workoutService) {
        this.workoutService = workoutService;
    }
    @Operation(summary = "Create a workout")
    @PostMapping
    public ResponseEntity<WorkoutDto> createWorkout(@RequestBody WorkoutDto workoutDto) {
        WorkoutDto savedWorkoutDto = workoutService.save(workoutDto);
        return ResponseEntity.ok(savedWorkoutDto);
    }
    @Operation(summary = "Get all workouts")
    @GetMapping
    public ResponseEntity<List<WorkoutDto>> getAllWorkouts() {
        List<WorkoutDto> allWorkoutsDto=workoutService.findAll();
        return ResponseEntity.ok(allWorkoutsDto);
    }
    @Operation(summary = "Get a workout by id")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<WorkoutDto>> getWorkoutById(@PathVariable Long id) {
        Optional<WorkoutDto> workoutByIdDto=workoutService.findById(id);
        return ResponseEntity.ok(workoutByIdDto);
    }
    @Operation(summary = "Get a workout by user id")
    @GetMapping("/user/{id}")
    public ResponseEntity<List<WorkoutSummaryDto>> getWorkoutByUserIdAndDate(@PathVariable Long id, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date!=null){
            return ResponseEntity.ok(workoutService.findWorkoutByUserIdAndByDate(id, date));
        }else{
            return ResponseEntity.ok(workoutService.findWorkoutByUserId(id));

        }
    }
    @Operation(summary = "Update a workout name")
    @PatchMapping("/{id}")
    public ResponseEntity<WorkoutDto> updateWorkout(@PathVariable Long id,@RequestParam String name) {
        WorkoutDto workoutDto=workoutService.update(id,name);
        return ResponseEntity.ok(workoutDto);
    }
    @Operation(summary = "Delete a workout by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        workoutService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
