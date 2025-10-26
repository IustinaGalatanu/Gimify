package com.example.Gymify.controller;

import com.example.Gymify.model.dto.ImageDto;
import com.example.Gymify.service.implementation.ImageServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/images")
@CrossOrigin("*")
@SecurityRequirement(name="BearerAuth")
public class ImageController {

    private final ImageServiceImpl imageService;

    public ImageController(ImageServiceImpl imageService) {
        this.imageService = imageService;
    }
    @Operation(summary = "Get all images")
    @GetMapping
    ResponseEntity<List<ImageDto>> getAllImages() {
        return ResponseEntity.ok(imageService.findAll());
    }

    @Operation(summary = "Get image by id")
    @GetMapping("/{id}")
    ResponseEntity<Optional<ImageDto>> getImageById(@PathVariable Long id) {
        return ResponseEntity.ok(imageService.findById(id));
    }
}


