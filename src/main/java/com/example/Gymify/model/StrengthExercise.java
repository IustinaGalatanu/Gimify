package com.example.Gymify.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "strength_exercises")
public class StrengthExercise extends Exercise {
    private Integer sets;
    private Integer rep;
    private Double weight;
}
