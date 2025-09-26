package com.example.Gymify.service.mapper;

import com.example.Gymify.model.Exercise;
import com.example.Gymify.model.ExerciseType;
import com.example.Gymify.model.Workout;
import com.example.Gymify.model.dto.ExerciseDto;
import org.springframework.stereotype.Component;

@Component
public class ExerciseMapper {

    public Exercise createFromDto(ExerciseDto exerciseDto, Workout workout, ExerciseType exerciseType){
        Exercise exercise=new Exercise();
        exercise.setId(exerciseDto.getId());
        exercise.setSets(exerciseDto.getSets());
        exercise.setRep(exerciseDto.getRep());
        exercise.setWeight(exerciseDto.getWeight());
        exercise.setWorkout(workout);
        exercise.setExerciseType(exerciseType);
        return exercise;
    }

    public ExerciseDto toDto(Exercise exercise){
        ExerciseDto exerciseDto =new ExerciseDto();
        exerciseDto.setId(exercise.getId());
        exerciseDto.setSets(exercise.getSets());
        exerciseDto.setRep(exercise.getRep());
        exerciseDto.setWeight(exercise.getWeight());
        if (exercise.getWorkout() != null) {
            exerciseDto.setWorkoutId(exercise.getWorkout().getId());
        }
        if (exercise.getExerciseType() != null) {
            exerciseDto.setExerciseTypeId(exercise.getExerciseType().getId());
        }
        return exerciseDto;
    }
}
