package com.example.Gymify.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "strength_exercises")
public class StrengthExercise extends Exercise {

    private Integer sets;
    private Integer reps;
    private Double weight;

    @Override
    public double calculateKcal() {
        if(sets==null || reps== null || weight==null) {
            return 0;
        }
        return sets*reps*weight*0.05*100/100;
    }
}
