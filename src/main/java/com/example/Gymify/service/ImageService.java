package com.example.Gymify.service;

import com.example.Gymify.model.dto.ImageDto;
import java.util.List;
import java.util.Optional;


public interface ImageService {

     List<ImageDto> findAll();
     Optional<ImageDto> findById(Long id);
}
