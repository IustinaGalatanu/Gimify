package com.example.Gymify.service.mapper;

import com.example.Gymify.model.ExerciseCatalog;
import com.example.Gymify.model.dto.ExerciseCatalogDto;
import org.springframework.stereotype.Component;

@Component
public class ExerciseCatalogMapper {

    public ExerciseCatalog createFromDto (ExerciseCatalogDto exerciseCatalogDto) {
        ExerciseCatalog exerciseCatalog = new ExerciseCatalog();
        exerciseCatalog.setId(exerciseCatalogDto.getId());
        exerciseCatalog.setType(exerciseCatalogDto.getType());
        exerciseCatalog.setName(exerciseCatalogDto.getName());
        return exerciseCatalog;
    }

    public ExerciseCatalogDto toDto (ExerciseCatalog exerciseCatalog) {
        ExerciseCatalogDto exerciseCatalogDto=new ExerciseCatalogDto();
        exerciseCatalogDto.setId(exerciseCatalog.getId());
        exerciseCatalogDto.setType(exerciseCatalog.getType());
        exerciseCatalogDto.setName(exerciseCatalog.getName());
        return exerciseCatalogDto;
    }
}
