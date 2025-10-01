package com.example.Gymify.service;

import com.example.Gymify.model.dto.WorkoutDto;
import com.example.Gymify.model.dto.WorkoutSummaryDto;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkoutService {

     WorkoutDto save(WorkoutDto workoutDto);

     Optional<WorkoutDto> findById(Long id);

     List<WorkoutDto> findAll();

     List<WorkoutSummaryDto> findWorkoutByUserIdAndByDate(Long id, LocalDate date);

     List<WorkoutSummaryDto> findWorkoutByUserId(Long id);

     WorkoutDto update (Long id, String name);

     void delete(Long id);
}

