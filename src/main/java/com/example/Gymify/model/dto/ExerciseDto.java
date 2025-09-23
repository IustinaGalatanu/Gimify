package com.example.Gymify.model.dto;

import jakarta.persistence.OneToMany;
import lombok.Data;
import org.aspectj.lang.annotation.Before;

@Data
public class ExerciseDto {

    private Long id;
    private String name;
    private int sets;
    private int rep;
    private double weight;
    private Long workoutId;

}
