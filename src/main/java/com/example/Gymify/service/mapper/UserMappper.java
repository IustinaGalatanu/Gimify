package com.example.Gymify.service.mapper;

import com.example.Gymify.model.Exercise;
import com.example.Gymify.model.User;
import com.example.Gymify.model.Workout;
import com.example.Gymify.model.dto.ExerciseDto;
import com.example.Gymify.model.dto.UserDto;
import com.example.Gymify.model.dto.WorkoutDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMappper {

    public User createFromDto(UserDto userDto){
        User user=new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setGoal(userDto.getGoal());
        user.setWorkouts(workoutsFromDto(userDto.getWorkouts(),user));
        return user;
    }

    private List<Workout> workoutsFromDto(List<WorkoutDto> workoutDtos, User user){
        // List<Workout> list = null
        // List<Workout> list = List.of(); -> []
        if(workoutDtos!=null && !workoutDtos.isEmpty()){
            List<Workout> workouts= workoutDtos.stream()
                    // List<Workout> list = List.of(); -> [null, "squats", null]
                    .filter(workoutDto -> workoutDto !=null )
                    .map(workoutDto -> workoutFromDto(workoutDto))
                    .collect(Collectors.toList());
            user.setWorkouts(workouts);
            return workouts;
        }
        return List.of();
    }

    private Workout workoutFromDto(WorkoutDto workoutDto){
        Workout workout =new Workout();
        workout.setId(workoutDto.getId());
        workout.setCreateTimestamp(workoutDto.getLocalDateTime());
        workout.setExercises(exercisesFromDto(workoutDto.getExercises(),workout));
        workout.setUser(createFromDto(workoutDto.getUser()));
        return workout;
    }

    private List<Exercise> exercisesFromDto(List<ExerciseDto> exerciseDtos, Workout workout){
        if (exerciseDtos != null && !exerciseDtos.isEmpty()) {
            List<Exercise> exercises =exerciseDtos.stream()
                    .filter(e -> e != null)
                    .map(e -> {
                        Exercise ex = new Exercise();
                        ex.setId(e.getId());
                        ex.setName(e.getName());
                        ex.setSets(e.getSets());
                        ex.setRep(e.getRep());
                        ex.setWeight(e.getWeight());
                        ex.setWorkout(workout);
                        return ex;
                    })
                    .collect(Collectors.toList());
            workout.setExercises(exercises);
            return exercises;
        }
        return List.of();
    }

    public UserDto toDto(User user){
        UserDto userDto =new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setGoal(user.getGoal());
        userDto.setWorkouts(workoutToDtos(user.getWorkouts(),userDto));
        return userDto;
    }

    private List<ExerciseDto> exerciseToDtos(List<Exercise> exercises, WorkoutDto workoutDto){
        if(exercises!=null && !exercises.isEmpty()){
            List<ExerciseDto> exerciseDtos = exercises.stream()
                    .map(exercise -> {
                        ExerciseDto exDto =new ExerciseDto();
                        exDto.setName(exercise.getName());
                        exDto.setSets(exercise.getSets());
                        exDto.setRep(exercise.getRep());
                        exDto.setWeight(exercise.getWeight());
                        return exDto;
                    })
                    .collect(Collectors.toList());
            workoutDto.setExercises(exerciseDtos);
            return exerciseDtos;
        }
        return List.of();
    }

    private List<WorkoutDto> workoutToDtos(List<Workout> workouts, UserDto userDto){
        if(workouts!=null){
            List<WorkoutDto> workoutsDtos =workouts.stream()
                    .map(workout ->{
                        WorkoutDto workoutDto =new WorkoutDto();
                        workoutDto.setId(workout.getId());
                        workoutDto.setLocalDateTime(workout.getCreateTimestamp());
                        workoutDto.setExercises(exerciseToDtos(workout.getExercises(),workoutDto));
                        return workoutDto;
                    })
                    .collect(Collectors.toList());
            userDto.setWorkouts(workoutsDtos);
            return workoutsDtos;
        }
        return List.of();
    }
}
