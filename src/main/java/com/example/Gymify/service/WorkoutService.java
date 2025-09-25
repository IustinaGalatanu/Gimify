package com.example.Gymify.service;

import com.example.Gymify.model.User;
import com.example.Gymify.model.Workout;
import com.example.Gymify.model.dto.WorkoutDto;
import com.example.Gymify.model.dto.WorkoutSummaryDto;
import com.example.Gymify.repository.UserRepository;
import com.example.Gymify.repository.WorkoutRepository;
import com.example.Gymify.service.mapper.WorkoutMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final WorkoutMapper workoutMapper;
    private final UserRepository userRepository;

    public WorkoutService(WorkoutRepository workoutRepository, WorkoutMapper workoutMapper, UserRepository userRepository) {
        this.workoutRepository = workoutRepository;
        this.workoutMapper = workoutMapper;
        this.userRepository = userRepository;
    }

    public WorkoutDto save(WorkoutDto workoutDto){
        Optional<User> optionalUser= userRepository.findById(workoutDto.getUserId());
        if(optionalUser.isEmpty()) {
            throw new RuntimeException("Could not create workout because user with id=" + workoutDto.getUserId() + " doesn't exist");
        }
        Workout workout=workoutMapper.createFromDto(workoutDto,optionalUser.get());
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

    public List<WorkoutSummaryDto> findWorkoutByUserIdAndByDate(Long id, LocalDate date){
        List<Workout> listWorkout= workoutRepository.findWorkoutByUserIdAndCreateTimestamp(id,date);
        List<WorkoutSummaryDto> listWorkoutSummaryDto =listWorkout.stream()
                .map(workoutMapper::toSummaryDto)
                .collect(Collectors.toList());
        return listWorkoutSummaryDto;
    }

    public List<WorkoutSummaryDto> findWorkoutByUserId(Long id) {
        List<Workout> listWorkout= workoutRepository.findWorkoutByUserId(id);
        List<WorkoutSummaryDto> listWorkoutSummaryDto =listWorkout.stream()
                .map(workoutMapper::toSummaryDto)
                .collect(Collectors.toList());
        return listWorkoutSummaryDto;
    }

    public void delete(Long id){
        workoutRepository.deleteById(id);
    }
}
