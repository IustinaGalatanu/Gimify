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
    private String name;
    private int sets;
    private int rep;
    private double weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="workout_id")
    private Workout workout;
}
