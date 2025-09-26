package com.example.Gymify.service.mapper;

import com.example.Gymify.model.Image;
import com.example.Gymify.model.dto.ImageDto;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {

    public Image createFromDto(ImageDto imageDto){
        Image image=new Image();
        image.setId(imageDto.getId());
        image.setUrl(imageDto.getUrl());
        return image;
    }

    public ImageDto toDto(Image image) {
        ImageDto imageDto=new ImageDto();
        imageDto.setId(image.getId());
        imageDto.setUrl(image.getUrl());
        return imageDto;
    }
}
