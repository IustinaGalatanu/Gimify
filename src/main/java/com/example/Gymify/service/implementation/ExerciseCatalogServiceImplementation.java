package com.example.Gymify.service.implementation;

import com.example.Gymify.model.ExerciseCatalog;
import com.example.Gymify.model.dto.ExerciseCatalogDto;
import com.example.Gymify.repository.ExerciseCatalogRepository;
import com.example.Gymify.service.ExerciseCatalogService;
import com.example.Gymify.service.mapper.ExerciseCatalogMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseCatalogServiceImplementation implements ExerciseCatalogService {

    private final ExerciseCatalogMapper exerciseCatalogMapper;
    private final ExerciseCatalogRepository exerciseCatalogRepository;

    public ExerciseCatalogServiceImplementation(ExerciseCatalogMapper exerciseCatalogMapper, ExerciseCatalogRepository exerciseCatalogRepository) {
        this.exerciseCatalogMapper = exerciseCatalogMapper;
        this.exerciseCatalogRepository = exerciseCatalogRepository;
    }

    public List<ExerciseCatalogDto> findAll(){
        List<ExerciseCatalog> exerciseCatalogListList =exerciseCatalogRepository.findAll();
        List<ExerciseCatalogDto> exerciseCatalogDtoListDtoList= exerciseCatalogListList.stream()
                .map(exerciseCatalogMapper::toDto)
                .collect(Collectors.toList());
        return exerciseCatalogDtoListDtoList;
    }

    public List<ExerciseCatalogDto> findByType(String type){
        List<ExerciseCatalog> exerciseList = exerciseCatalogRepository.findByTypeIgnoreCase(type);
        List<ExerciseCatalogDto> exerciseDtoList = exerciseList.stream()
                .map(exercise ->exerciseCatalogMapper.toDto(exercise))
                .collect(Collectors.toList());
        return exerciseDtoList;
    }
}
