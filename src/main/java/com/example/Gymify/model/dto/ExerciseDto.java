package com.example.Gymify.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExerciseDto {

    private Long id;
    private Integer sets;
    private Integer reps;
    private Double weight;
    private Integer duration;
    private Long workoutId;
    private Long catalogId;
    private Double kcal;

}
