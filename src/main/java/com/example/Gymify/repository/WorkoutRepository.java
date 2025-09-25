package com.example.Gymify.repository;

import com.example.Gymify.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout,Long> {
    List<Workout> findWorkoutByUserIdAndCreateTimestamp(Long userId, LocalDate date);
    List<Workout> findWorkoutByUserId(Long id);
}
