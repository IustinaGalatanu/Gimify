package com.example.Gymify.service.mapper;

import com.example.Gymify.model.ExerciseType;
import com.example.Gymify.model.dto.ExerciseTypeDto;
import org.springframework.stereotype.Component;

@Component
public class ExerciseTypeMapper {

    public ExerciseType createFromDto(ExerciseTypeDto exerciseTypeDto) {
        ExerciseType exerciseType=new ExerciseType();
        exerciseType.setId(exerciseTypeDto.getId());
        exerciseType.setType(exerciseTypeDto.getType());
        return exerciseType;
    }

    public ExerciseTypeDto toDto (ExerciseType exerciseType) {
        ExerciseTypeDto exerciseTypeDto = new ExerciseTypeDto();
        exerciseTypeDto.setId(exerciseType.getId());
        exerciseTypeDto.setType(exerciseType.getType());
        return exerciseTypeDto;
    }
}
