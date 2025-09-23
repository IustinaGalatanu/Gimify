package com.example.Gymify.service;

import com.example.Gymify.model.Workout;
import com.example.Gymify.model.dto.WorkoutDto;
import com.example.Gymify.repository.WorkoutRepository;
import com.example.Gymify.service.mapper.WorkoutMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final WorkoutMapper workoutMapper;

    public WorkoutService(WorkoutRepository workoutRepository, WorkoutMapper workoutMapper) {
        this.workoutRepository = workoutRepository;
        this.workoutMapper = workoutMapper;
    }

    public WorkoutDto save(WorkoutDto workoutDto){
        Workout workout=workoutMapper.createFromDto(workoutDto);
        Workout savedWorkout = workoutRepository.save(workout);
        return workoutMapper.toDto(savedWorkout);
    }

    public Optional<WorkoutDto> findById(Long id){
        Optional<Workout> optionalWorkout = workoutRepository.findById(id);
        Optional<WorkoutDto> optionalWorkoutDto = optionalWorkout.map(workoutMapper::toDto);
        return optionalWorkoutDto;
    }

    public List<WorkoutDto> findAll(){
        List<Workout> listWorkout= workoutRepository.findAll();
        List<WorkoutDto> workoutDtoList=listWorkout.stream()
                .map(workoutMapper::toDto)
                .collect(Collectors.toList());
        return workoutDtoList;
    }

    public void delete(Long id){
        workoutRepository.deleteById(id);
    }
}
