package com.example.Gymify.service;

import com.example.Gymify.model.dto.ExerciseDto;
import java.util.List;
import java.util.Optional;

public interface ExerciseService {

     ExerciseDto save(ExerciseDto exerciseDto);

     Optional<ExerciseDto> findById(Long id);

     List<ExerciseDto> findAll();

     List<ExerciseDto> findExerciseByWorkoutId(Long id);

     ExerciseDto update(Long id, Long catalogId );

     ExerciseDto update (Long id, ExerciseDto exerciseDto) throws Exception;

     void delete (Long id);
}
