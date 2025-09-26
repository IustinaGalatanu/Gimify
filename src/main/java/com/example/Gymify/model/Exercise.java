package com.example.Gymify.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;
    private int sets;
    private int rep;
    private double weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="workout_id")
    private Workout workout;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="exercise_type_id")
    private ExerciseType exerciseType;

}
