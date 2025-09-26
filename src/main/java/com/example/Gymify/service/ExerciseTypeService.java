package com.example.Gymify.service;

import com.example.Gymify.model.ExerciseType;
import com.example.Gymify.model.dto.ExerciseDto;
import com.example.Gymify.model.dto.ExerciseTypeDto;
import com.example.Gymify.repository.ExerciseTypeRepository;
import com.example.Gymify.service.mapper.ExerciseTypeMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseTypeService {

    private final ExerciseTypeRepository exerciseTypeRepository;
    private final ExerciseTypeMapper exerciseTypeMapper;

    public ExerciseTypeService(ExerciseTypeRepository exerciseTypeRepository, ExerciseTypeMapper exerciseTypeMapper) {
        this.exerciseTypeRepository = exerciseTypeRepository;
        this.exerciseTypeMapper = exerciseTypeMapper;
    }

    public final List<ExerciseTypeDto> findAll() {
        List<ExerciseType> exerciseTypeList=exerciseTypeRepository.findAll();
        List<ExerciseTypeDto> exerciseDtoList= exerciseTypeList.stream()
                .map(exerciseTypeMapper::toDto)
                .collect(Collectors.toList());
        return exerciseDtoList;
    }
}
