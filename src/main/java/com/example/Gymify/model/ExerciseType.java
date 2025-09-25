package com.example.Gymify.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="exerciseTypes")
public class ExerciseType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;

//    @OneToMany(mappedBy = "exercises")
//    private List<Exercise> exercises;
}
