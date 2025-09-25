package com.example.Gymify.service.mapper;

import com.example.Gymify.model.Exercise;
import com.example.Gymify.model.Workout;
import com.example.Gymify.model.dto.ExerciseDto;
import org.springframework.stereotype.Component;

@Component
public class ExerciseMapper {

    public Exercise createFromDto(ExerciseDto exerciseDto, Workout workout){
        Exercise exercise=new Exercise();
        exercise.setId(exerciseDto.getId());
        exercise.setName(exerciseDto.getName());
        exercise.setSets(exerciseDto.getSets());
        exercise.setRep(exerciseDto.getRep());
        exercise.setWeight(exerciseDto.getWeight());
        exercise.setWorkout(workout);
        return exercise;
    }

    public ExerciseDto toDto(Exercise exercise){
        ExerciseDto exerciseDto =new ExerciseDto();
        exerciseDto.setId(exercise.getId());
        exerciseDto.setName(exercise.getName());
        exerciseDto.setSets(exercise.getSets());
        exerciseDto.setRep(exercise.getRep());
        exerciseDto.setWeight(exercise.getWeight());
        if (exercise.getWorkout() != null) {
            exerciseDto.setWorkoutId(exercise.getWorkout().getId());
        }

        return exerciseDto;
    }
}
