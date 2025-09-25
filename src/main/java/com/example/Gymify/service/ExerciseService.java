package com.example.Gymify.service;

import com.example.Gymify.model.Exercise;
import com.example.Gymify.model.Workout;
import com.example.Gymify.model.dto.ExerciseDto;
import com.example.Gymify.repository.ExerciseRepository;
import com.example.Gymify.repository.WorkoutRepository;
import com.example.Gymify.service.mapper.ExerciseMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;
    private final WorkoutRepository workoutRepository;

    public ExerciseService(ExerciseRepository exerciseRepository, ExerciseMapper exerciseMapper, WorkoutRepository workoutRepository){
        this.exerciseRepository=exerciseRepository;
        this.exerciseMapper = exerciseMapper;
        this.workoutRepository = workoutRepository;
    }

    public ExerciseDto save(ExerciseDto exerciseDto){
        Workout workout = workoutRepository.findById(exerciseDto.getWorkoutId())
                .orElseThrow(() -> new RuntimeException("Workout not found"));
        Exercise exercise=exerciseMapper.createFromDto(exerciseDto,workout);
        Exercise savedExercise=exerciseRepository.save(exercise);
        return exerciseMapper.toDto(savedExercise);
    }

    public Optional<ExerciseDto> findById(Long id){
        Optional<Exercise> optionalExercise=exerciseRepository.findById(id);
        Optional<ExerciseDto> optionalExerciseDto=optionalExercise.map(exerciseMapper::toDto);
        return optionalExerciseDto;
    }

    public List<ExerciseDto> findAll(){
        List<Exercise> exerciseList=exerciseRepository.findAll();
        List<ExerciseDto> exerciseDtoList=exerciseList.stream()
                .map(exerciseMapper::toDto)
                .collect(Collectors.toList());
        return exerciseDtoList;
    }

    public void delete (Long id){
        exerciseRepository.deleteById(id);
    }
}
