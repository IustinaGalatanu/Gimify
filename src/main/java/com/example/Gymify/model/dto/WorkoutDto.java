package com.example.Gymify.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WorkoutDto {

    private Long id;
    private String name;
    private LocalDateTime createTimestamp;
    private Long userId;
    private List<ExerciseDto> exercises;
}
