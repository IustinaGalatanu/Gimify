package com.example.Gymify.service.mapper;

import com.example.Gymify.model.*;
import com.example.Gymify.model.dto.ExerciseDto;
import org.springframework.stereotype.Component;

@Component
public class ExerciseMapper {

    public Exercise createFromDto(ExerciseDto exerciseDto, Workout workout, ExerciseCatalog catalogExercise) {
        if ("STRENGTH".equalsIgnoreCase(catalogExercise.getType())) {
            StrengthExercise strengthExercise = new StrengthExercise();
            strengthExercise.setId(exerciseDto.getId());
            strengthExercise.setWorkout(workout);
            strengthExercise.setExerciseCatalog(catalogExercise);
            strengthExercise.setSets(exerciseDto.getSets());
            strengthExercise.setReps(exerciseDto.getReps());
            strengthExercise.setWeight(exerciseDto.getWeight());
            return strengthExercise;
        } else if ("CARDIO".equalsIgnoreCase(catalogExercise.getType())) {
            CardioExercise cardioExercise = new CardioExercise();
            cardioExercise.setId(exerciseDto.getId());
            cardioExercise.setWorkout(workout);
            cardioExercise.setExerciseCatalog(catalogExercise);
            cardioExercise.setDuration(exerciseDto.getDuration());
            return cardioExercise;
        }
        return null;
    }

    public ExerciseDto toDto(Exercise exercise){
        ExerciseDto exerciseDto =new ExerciseDto();
        exerciseDto.setId(exercise.getId());
        if (exercise.getWorkout() != null) {
            exerciseDto.setWorkoutId(exercise.getWorkout().getId());
       }
        if (exercise.getExerciseCatalog() != null) {
            exerciseDto.setCatalogId(exercise.getExerciseCatalog().getId());
        }

       if(exercise instanceof StrengthExercise strengthExercise){
           exerciseDto.setSets(strengthExercise.getSets());
           exerciseDto.setReps(strengthExercise.getReps());
           exerciseDto.setWeight(strengthExercise.getWeight());
       }else if( exercise instanceof  CardioExercise cardioExercise){
           exerciseDto.setDuration(cardioExercise.getDuration());
       }
        return exerciseDto;
    }
}
