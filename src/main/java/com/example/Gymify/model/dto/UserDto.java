package com.example.Gymify.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String goal;
    private List<WorkoutDto> workouts;
    private Long imageId;
}
