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
public class WorkoutMapper {
    public Workout createFromDto(WorkoutDto workoutDto){
        Workout workout=new Workout();
        workout.setId(workoutDto.getId());
        workout.setCreateTimestamp(workoutDto.getLocalDateTime());
        workout.setExercises(exerciseFromDto(workoutDto.getExercises(),workout));
        workout.setUser(userFromDto(workoutDto.getUser(),workout));
        return workout;
    }



    public List<Exercise> exerciseFromDto(List<ExerciseDto> exerciseDto, Workout workout){
        if(exerciseDto!=null && !exerciseDto.isEmpty()){
            List<Exercise> exercises= exerciseDto.stream()
                    .filter(exDto->exDto!=null && !exDto.getName().isBlank())
                    .map(exerciseDto1 ->{Exercise exercise = new Exercise();
                        exercise.setName(exerciseDto1.getName());
                        exercise.setSets(exerciseDto1.getSets());
                        exercise.setRep(exerciseDto1.getRep());
                        exercise.setWeight(exerciseDto1.getWeight());
                        return exercise;})
                    .collect(Collectors.toList());
            workout.setExercises(exercises);
            return exercises;
        }
        return List.of();
    }

    public User userFromDto(UserDto userDto,Workout workout){
        User user=new User();
        user.setId(userDto.getId());
        workout.setUser(user);
        return user;
    }

    public WorkoutDto toDto(Workout workout){
        WorkoutDto workoutDto =new WorkoutDto();
        workoutDto.setId(workout.getId());
        workoutDto.setLocalDateTime(workout.getCreateTimestamp());
        workoutDto.setExercises(exerciseToDtos(workout.getExercises(),workoutDto));
        workoutDto.setUser(userToDto(workout.getUser(),workoutDto));
        return workoutDto;
    }


    public List<ExerciseDto> exerciseToDtos(List<Exercise> exercise,WorkoutDto workoutDto){
        if(exercise!=null){
            List<ExerciseDto> exercisesDtos = exercise.stream()
                    .map(exerc->{
                        ExerciseDto exerciseDto =new ExerciseDto();
                        exerciseDto.setName(exerc.getName());
                        exerciseDto.setSets(exerc.getSets());
                        exerciseDto.setRep(exerc.getRep());
                        exerciseDto.setWeight(exerc.getWeight());
                        return exerciseDto;
                    })
                    .collect(Collectors.toList());
            workoutDto.setExercises(exercisesDtos);
            return exercisesDtos;
        }
        return List.of();
    }

    public UserDto userToDto(User user,WorkoutDto workoutDto){
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setGoal(user.getGoal());

        return userDto;
    }
}
