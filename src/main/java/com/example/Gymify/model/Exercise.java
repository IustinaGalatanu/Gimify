package com.example.Gymify.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="exercises")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Exercise {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="workout_id")
    private Workout workout;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "exercise_catalog_id")
    private ExerciseCatalog exerciseCatalog;


}
