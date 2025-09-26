package com.example.Gymify.model.dto;


import lombok.Data;

@Data
public class ExerciseDto {

    private Long id;
    private Integer sets;
    private Integer rep;
    private Double weight;
    private Long workoutId;
    private Long exerciseTypeId;

}
