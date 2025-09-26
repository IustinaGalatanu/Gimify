package com.example.Gymify.service;

import com.example.Gymify.model.Image;
import com.example.Gymify.model.dto.ImageDto;
import com.example.Gymify.repository.ImageRepository;
import com.example.Gymify.service.mapper.ImageMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService {

    private final ImageMapper imageMapper;
    private final ImageRepository imageRepository;

    public ImageService(ImageMapper imageMapper, ImageRepository imageRepository) {
        this.imageMapper = imageMapper;
        this.imageRepository = imageRepository;
    }

    public List<ImageDto> findAll() {
        List<Image> imageList = imageRepository.findAll();
        List<ImageDto> imageDtoList=imageList.stream()
                .map(imageMapper::toDto)
                .collect(Collectors.toList());
        return imageDtoList;
    }

}
