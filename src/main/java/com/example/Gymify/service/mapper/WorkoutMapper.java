package com.example.Gymify.service.mapper;

import com.example.Gymify.model.*;
import com.example.Gymify.model.dto.ExerciseDto;
import com.example.Gymify.model.dto.UserDto;
import com.example.Gymify.model.dto.WorkoutDto;
import com.example.Gymify.model.dto.WorkoutSummaryDto;
import com.example.Gymify.repository.ExerciseCatalogRepository;
import com.example.Gymify.repository.ExerciseRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorkoutMapper {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseCatalogRepository exerciseCatalogRepository;

    public WorkoutMapper(ExerciseRepository exerciseRepository, ExerciseCatalogRepository exerciseCatalogRepository) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseCatalogRepository = exerciseCatalogRepository;
    }

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
                    .filter(exDto->exDto!=null )
                    .map(exerciseDto1 -> {
                        ExerciseCatalog catalog = exerciseCatalogRepository.findById(exerciseDto1.getCatalogId())
                                .orElseThrow(() -> new RuntimeException("Catalog not found: " + exerciseDto1.getCatalogId()));
                        return exerciseFromDto(exerciseDto1, workout, catalog);
                    })
                    .collect(Collectors.toList());
            workout.setExercises(exercises);
            return exercises;
        }
        return List.of();
    }

    private Exercise exerciseFromDto (ExerciseDto exerciseDto, Workout workout, ExerciseCatalog catalog){
        if ("STRENGTH".equalsIgnoreCase(catalog.getType())) {
            StrengthExercise strength = new StrengthExercise();
            strength.setId(exerciseDto.getId());
            strength.setWorkout(workout);
            strength.setExerciseCatalog(catalog);
            strength.setSets(exerciseDto.getSets());
            strength.setRep(exerciseDto.getRep());
            strength.setWeight(exerciseDto.getWeight());
            return strength;
        } else if ("CARDIO".equalsIgnoreCase(catalog.getType())) {
            CardioExercise cardio = new CardioExercise();
            cardio.setId(exerciseDto.getId());
            cardio.setWorkout(workout);
            cardio.setExerciseCatalog(catalog);
            cardio.setDuration(exerciseDto.getDuration());
            return cardio;
        }
        return null;
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

    public WorkoutSummaryDto toSummaryDto(Workout workout){
        WorkoutSummaryDto workoutSummaryDto=new WorkoutSummaryDto();
        workoutSummaryDto.setId(workout.getId());
        workoutSummaryDto.setName(workout.getName());
        workoutSummaryDto.setCreateTimestamp(workout.getCreateTimestamp());
        return workoutSummaryDto;
    }


    public List<ExerciseDto> exerciseToDtos(List<Exercise> exercise,WorkoutDto workoutDto){
        if(exercise!=null){
            List<ExerciseDto> exercisesDtos = exercise.stream()
                    .map(exerc->{
                        ExerciseDto exerciseDto =new ExerciseDto();
                        exerciseDto.setId(exerc.getId());
                        if (exerc.getWorkout() != null) {
                            exerciseDto.setWorkoutId(exerc.getWorkout().getId());
                        }
                        if (exerc.getExerciseCatalog() != null) {
                            exerciseDto.setCatalogId(exerc.getExerciseCatalog().getId());
                        }
                        if(exerc instanceof StrengthExercise strengthExercise){
                            exerciseDto.setSets(strengthExercise.getSets());
                            exerciseDto.setRep(strengthExercise.getRep());
                            exerciseDto.setWeight(strengthExercise.getWeight());
                        }else if( exerc instanceof  CardioExercise cardioExercise){
                            exerciseDto.setDuration(cardioExercise.getDuration());
                        }
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
