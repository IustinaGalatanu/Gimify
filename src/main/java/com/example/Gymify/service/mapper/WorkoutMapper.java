package com.example.Gymify.service.mapper;

import com.example.Gymify.model.*;
import com.example.Gymify.model.dto.ExerciseDto;
import com.example.Gymify.model.dto.UserDto;
import com.example.Gymify.model.dto.WorkoutDto;
import com.example.Gymify.model.dto.WorkoutSummaryDto;
import com.example.Gymify.repository.ExerciseCatalogRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorkoutMapper {


    private final ExerciseCatalogRepository exerciseCatalogRepository;

    public WorkoutMapper(ExerciseCatalogRepository exerciseCatalogRepository) {
        this.exerciseCatalogRepository = exerciseCatalogRepository;
    }

    public Workout createFromDto (WorkoutDto workoutDto, User user){
        Workout workout=new Workout();
        workout.setId(workoutDto.getId());
        workout.setName(workoutDto.getName());
        workout.setCreateTimestamp(workoutDto.getCreateTimestamp());
        List<Exercise> exercises=exercisesFromDto(workoutDto.getExercises(),workout);
        workout.setExercises(exercises);
        if (exercises != null) {
            exercises.forEach(exercise -> exercise.setWorkout(workout));
        }
        workout.setUser(user);
        return workout;
    }

    public List<Exercise> exercisesFromDto (List<ExerciseDto> exercisesDto, Workout workout){
        if(exercisesDto !=null && !exercisesDto.isEmpty()){
            List<Exercise> exercises= exercisesDto.stream()
                    .filter(exerciseDto->exerciseDto!=null )
                    .map(exerciseDto -> {
                        ExerciseCatalog exerciseCatalog = exerciseCatalogRepository.findById(exerciseDto.getCatalogId())
                                .orElseThrow(() -> new RuntimeException("Catalog not found: " + exerciseDto.getCatalogId()));
                        return exerciseFromDto(exerciseDto, workout, exerciseCatalog);
                    })
                    .collect(Collectors.toList());
            workout.setExercises(exercises);
            return exercises;
        }
        return List.of();
    }

    private Exercise exerciseFromDto (ExerciseDto exerciseDto, Workout workout, ExerciseCatalog exerciseCatalog){
        if ("STRENGTH".equalsIgnoreCase(exerciseCatalog.getType())) {
            StrengthExercise strengthExercise = new StrengthExercise();
            strengthExercise.setId(exerciseDto.getId());
            strengthExercise.setWorkout(workout);
            strengthExercise.setExerciseCatalog(exerciseCatalog);
            strengthExercise.setSets(exerciseDto.getSets());
            strengthExercise.setReps(exerciseDto.getReps());
            strengthExercise.setWeight(exerciseDto.getWeight());
            return strengthExercise;
        } else if ("CARDIO".equalsIgnoreCase(exerciseCatalog.getType())) {
            CardioExercise cardioExercise = new CardioExercise();
            cardioExercise.setId(exerciseDto.getId());
            cardioExercise.setWorkout(workout);
            cardioExercise.setExerciseCatalog(exerciseCatalog);
            cardioExercise.setDuration(exerciseDto.getDuration());
            return cardioExercise;
        }
        return null;
    }

    public User userFromDto (UserDto userDto,Workout workout){
        User user=new User();
        user.setId(userDto.getId());
        workout.setUser(user);
        return user;
    }

    public WorkoutDto toDto (Workout workout){
        WorkoutDto workoutDto =new WorkoutDto();
        workoutDto.setId(workout.getId());
        workoutDto.setCreateTimestamp(workout.getCreateTimestamp());
        workoutDto.setName(workout.getName());
        workoutDto.setExercises(exercisesToDto(workout.getExercises(),workoutDto));
        workoutDto.setUserId(workout.getUser().getId());
        return workoutDto;
    }

    public WorkoutSummaryDto toSummaryDto (Workout workout){
        WorkoutSummaryDto workoutSummaryDto=new WorkoutSummaryDto();
        workoutSummaryDto.setId(workout.getId());
        workoutSummaryDto.setName(workout.getName());
        workoutSummaryDto.setCreateTimestamp(workout.getCreateTimestamp());
        return workoutSummaryDto;
    }


    public List<ExerciseDto> exercisesToDto (List<Exercise> exercises, WorkoutDto workoutDto){
        if(exercises !=null){
            List<ExerciseDto> exercisesDto = exercises.stream()
                    .map(exercise ->{
                        ExerciseDto exerciseDto =new ExerciseDto();
                        exerciseDto.setId(exercise.getId());
                        if (exercise.getWorkout() != null) {
                            exerciseDto.setWorkoutId(exercise.getWorkout().getId());
                        }
                        if (exercise.getExerciseCatalog() != null) {
                            exerciseDto.setCatalogId(exercise.getExerciseCatalog().getId());
                        }
                        if(exercise instanceof StrengthExercise strengthExercise){
                            exerciseDto.setSets(strengthExercise.getSets());
                            exerciseDto.setReps(strengthExercise.getReps());
                            exerciseDto.setWeight(strengthExercise.getWeight());
                        }else if( exercise instanceof  CardioExercise cardioExercise){
                            exerciseDto.setDuration(cardioExercise.getDuration());
                        }
                        return exerciseDto;
                    })
                    .toList();
            workoutDto.setExercises(exercisesDto);
            return exercisesDto;
        }
        return List.of();
    }

    public UserDto userToDto (User user, WorkoutDto workoutDto){
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setGoal(user.getGoal());
        return userDto;
    }
}
