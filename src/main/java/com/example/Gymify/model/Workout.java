package com.example.Gymify.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="workouts")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createTimestamp;

    @OneToMany(mappedBy = "workout")
    private List<Exercise> exercises;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
