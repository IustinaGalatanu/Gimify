package com.example.Gymify.service.mapper;

import com.example.Gymify.model.Exercise;
import com.example.Gymify.model.dto.ExerciseDto;
import org.springframework.stereotype.Component;

@Component
public class ExerciseMapper {

    public Exercise createFromDto(ExerciseDto dto){
        Exercise exercise=new Exercise();
        exercise.setId(dto.getId());
        exercise.setName(dto.getName());
        exercise.setSets(dto.getSets());
        exercise.setRep(dto.getRep());
        exercise.setWeight(dto.getWeight());
        return exercise;
    }

    public ExerciseDto toDto(Exercise exercise){
        ExerciseDto exerciseDtodto=new ExerciseDto();
        exerciseDtodto.setId(exercise.getId());
        exerciseDtodto.setName(exercise.getName());
        exerciseDtodto.setSets(exercise.getSets());
        exerciseDtodto.setRep(exercise.getRep());
        exerciseDtodto.setWeight(exercise.getWeight());
        return exerciseDtodto;
    }
}
