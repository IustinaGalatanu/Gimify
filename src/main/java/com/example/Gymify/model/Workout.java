package com.example.Gymify.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="workouts")
@ToString(exclude = "user")
@EqualsAndHashCode(exclude ="user" )
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime createTimestamp;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL)
    private List<Exercise> exercises;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
