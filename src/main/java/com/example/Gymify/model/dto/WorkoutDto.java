package com.example.Gymify.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class WorkoutDto {

    private Long id;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createTimestamp;
    private Long userId;
    private List<ExerciseDto> exercises;
    private Double totalKcal;
}
