package com.example.Gymify.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "cardio_exercises")
public class CardioExercise extends Exercise{
    private Integer duration;
}
