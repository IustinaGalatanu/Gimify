package com.example.Gymify.repository;

import com.example.Gymify.model.ExerciseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseTypeRepository extends JpaRepository<ExerciseType,Long> {
}
