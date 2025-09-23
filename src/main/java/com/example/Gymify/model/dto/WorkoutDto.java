package com.example.Gymify.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WorkoutDto {

    private Long id;
    private UserDto user;
    private LocalDateTime localDateTime;
    private List<ExerciseDto> exercises;
}
