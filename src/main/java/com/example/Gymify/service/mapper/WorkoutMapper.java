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
    public Workout createFromDto(WorkoutDto workoutDto, User user){
        Workout workout=new Workout();
        workout.setId(workoutDto.getId());
        workout.setName(workoutDto.getName());
        workout.setCreateTimestamp(workoutDto.getCreateTimestamp());
        List<Exercise> exercises=exerciseFromDto(workoutDto.getExercises(),workout);
        workout.setExercises(exercises);
        if (exercises != null) {
            exercises.forEach(e -> e.setWorkout(workout));
        }
        workout.setUser(user);
        return workout;
    }

    public List<Exercise> exerciseFromDto(List<ExerciseDto> exerciseDto, Workout workout){
        if(exerciseDto!=null && !exerciseDto.isEmpty()){
            List<Exercise> exercises= exerciseDto.stream()
                    .filter(exDto->exDto!=null && !exDto.getName().isBlank())
                    .map(exerciseDto1 ->exerciseFromDto(exerciseDto1,workout))
                    .collect(Collectors.toList());
            workout.setExercises(exercises);
            return exercises;
        }
        return List.of();
    }

    private Exercise exerciseFromDto (ExerciseDto exerciseDto,Workout workout){
        Exercise exercise= new Exercise();
        exercise.setId(exerciseDto.getId());
        exercise.setName(exerciseDto.getName());
        exercise.setSets(exerciseDto.getSets());
        exercise.setRep(exerciseDto.getRep());
        exercise.setWeight(exerciseDto.getWeight());
        exercise.setWorkout(workout);
        return exercise;
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
        workoutDto.setCreateTimestamp(workout.getCreateTimestamp());
        workoutDto.setName(workout.getName());
        workoutDto.setExercises(exerciseToDtos(workout.getExercises(),workoutDto));
        workoutDto.setUserId(workout.getUser().getId());
        return workoutDto;
    }


    public List<ExerciseDto> exerciseToDtos(List<Exercise> exercise,WorkoutDto workoutDto){
        if(exercise!=null){
            List<ExerciseDto> exercisesDtos = exercise.stream()
                    .map(exerc->{
                        ExerciseDto exerciseDto =new ExerciseDto();
                        exerciseDto.setId(exerc.getId());
                        exerciseDto.setName(exerc.getName());
                        exerciseDto.setSets(exerc.getSets());
                        exerciseDto.setRep(exerc.getRep());
                        exerciseDto.setWeight(exerc.getWeight());
                        exerciseDto.setWorkoutId(exerc.getWorkout().getId());

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
