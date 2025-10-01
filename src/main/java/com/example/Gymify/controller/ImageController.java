package com.example.Gymify.controller;

import com.example.Gymify.model.dto.ImageDto;
import com.example.Gymify.service.implementation.ImageServiceImplementation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/images")
@CrossOrigin("*")
public class ImageController {

    private final ImageServiceImplementation imageService;

    public ImageController(ImageServiceImplementation imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    ResponseEntity<List<ImageDto>> getAllImages() {
        return ResponseEntity.ok(imageService.findAll());
    }
}
