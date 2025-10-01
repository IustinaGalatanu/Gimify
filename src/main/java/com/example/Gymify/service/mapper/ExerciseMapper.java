package com.example.Gymify.service.mapper;

import com.example.Gymify.model.*;
import com.example.Gymify.model.dto.ExerciseDto;
import org.springframework.stereotype.Component;

@Component
public class ExerciseMapper {


    public Exercise createFromDto(ExerciseDto exerciseDto, Workout workout, ExerciseCatalog catalog) {
        if ("STRENGTH".equalsIgnoreCase(catalog.getType())) {
            StrengthExercise strength = new StrengthExercise();
            strength.setId(exerciseDto.getId());
            strength.setWorkout(workout);
            strength.setExerciseCatalog(catalog);
            strength.setSets(exerciseDto.getSets());
            strength.setRep(exerciseDto.getRep());
            strength.setWeight(exerciseDto.getWeight());
            return strength;
        } else if ("CARDIO".equalsIgnoreCase(catalog.getType())) {
            CardioExercise cardio = new CardioExercise();
            cardio.setId(exerciseDto.getId());
            cardio.setWorkout(workout);
            cardio.setExerciseCatalog(catalog);
            cardio.setDuration(exerciseDto.getDuration());
            return cardio;
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
           exerciseDto.setRep(strengthExercise.getRep());
           exerciseDto.setWeight(strengthExercise.getWeight());
       }else if( exercise instanceof  CardioExercise cardioExercise){
           exerciseDto.setDuration(cardioExercise.getDuration());
       }
        return exerciseDto;
    }
}
