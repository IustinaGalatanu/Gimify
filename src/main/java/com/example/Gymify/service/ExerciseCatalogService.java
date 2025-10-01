package com.example.Gymify.service;

import com.example.Gymify.model.dto.ExerciseCatalogDto;
import java.util.List;

public interface ExerciseCatalogService {

     List<ExerciseCatalogDto> findAll();

     List<ExerciseCatalogDto> findByType(String type);
}
