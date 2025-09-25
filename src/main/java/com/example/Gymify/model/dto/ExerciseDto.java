package com.example.Gymify.model.dto;


import lombok.Data;

@Data
public class ExerciseDto {

    private Long id;
    private String name;
    private int sets;
    private int rep;
    private double weight;
    private Long workoutId;

}
