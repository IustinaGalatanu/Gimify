package com.example.Gymify.service.implementation;

import com.example.Gymify.model.Image;
import com.example.Gymify.model.dto.ImageDto;
import com.example.Gymify.repository.ImageRepository;
import com.example.Gymify.service.ImageService;
import com.example.Gymify.service.mapper.ImageMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageMapper imageMapper;
    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageMapper imageMapper, ImageRepository imageRepository) {
        this.imageMapper = imageMapper;
        this.imageRepository = imageRepository;
    }

    public List<ImageDto> findAll() {
        List<Image> imagesList = imageRepository.findAll();
        List<ImageDto> imagesDtoList= imagesList.stream()
                .map(imageMapper::toDto)
                .collect(Collectors.toList());
        return imagesDtoList;
    }


    public Optional<ImageDto> findById( Long id) {
        Optional<Image> image=imageRepository.findById(id);
        return image.map(imageMapper::toDto);
    }

}
