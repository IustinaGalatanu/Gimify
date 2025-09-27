package com.example.Gymify.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;
    private Integer sets;
    private Integer rep;
    private Double weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="workout_id")
    private Workout workout;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="exercise_type_id")
    private ExerciseType exerciseType;

}
