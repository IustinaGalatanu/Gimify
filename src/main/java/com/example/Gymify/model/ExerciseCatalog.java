package com.example.Gymify.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "exercise_catalog")
public class ExerciseCatalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
}
