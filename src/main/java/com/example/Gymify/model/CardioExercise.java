package com.example.Gymify.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "cardio_exercises")
public class CardioExercise extends Exercise{

    private Integer duration;

    @Override
    public double calculateKcal() {
        if(duration== null){
            return 0;
        }
        return duration*8*100/100;
    }
}
