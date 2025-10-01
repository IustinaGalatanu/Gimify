package com.example.Gymify.service.implementation;

import com.example.Gymify.model.*;
import com.example.Gymify.model.dto.ExerciseDto;
import com.example.Gymify.repository.*;
import com.example.Gymify.service.ExerciseService;
import com.example.Gymify.service.mapper.ExerciseMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExerciseServiceImplementation implements ExerciseService {

    private final ExerciseCatalogRepository exerciseCatalogRepository;
    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;
    private final WorkoutRepository workoutRepository;

    public ExerciseServiceImplementation(ExerciseCatalogRepository exerciseCatalogRepository, ExerciseRepository exerciseRepository, ExerciseMapper exerciseMapper, WorkoutRepository workoutRepository){
        this.exerciseCatalogRepository = exerciseCatalogRepository;
        this.exerciseRepository = exerciseRepository;
        this.exerciseMapper = exerciseMapper;
        this.workoutRepository = workoutRepository;
    }

    public ExerciseDto save(ExerciseDto exerciseDto){
        Workout workout = workoutRepository.findById(exerciseDto.getWorkoutId())
                .orElseThrow(() -> new RuntimeException("Workout not found"));
        ExerciseCatalog catalog=exerciseCatalogRepository.findById(exerciseDto.getCatalogId())
                .orElseThrow(()-> new RuntimeException("Exercise catalog not found"));
        Exercise exercise=exerciseMapper.createFromDto(exerciseDto,workout,catalog);
        Exercise savedExercise=exerciseRepository.save(exercise);
        return exerciseMapper.toDto(savedExercise);
    }

    public Optional<ExerciseDto> findById(Long id){
        Optional<Exercise> optionalExercise=exerciseRepository.findById(id);
        Optional<ExerciseDto> optionalExerciseDto=optionalExercise.map(exerciseMapper::toDto);
        return optionalExerciseDto;
    }

    public List<ExerciseDto> findAll(){
        List<Exercise> exercisesList =exerciseRepository.findAll();
        List<ExerciseDto> exercisesDtoList= exercisesList.stream()
                .map(exerciseMapper::toDto)
                .collect(Collectors.toList());
        return exercisesDtoList;
    }

    public List<ExerciseDto> findExerciseByWorkoutId(Long id) {
        List<Exercise> exerciseList=exerciseRepository.findExerciseByWorkoutId(id);
        List<ExerciseDto> exerciseDtoList=exerciseList.stream()
                .map(exerciseMapper::toDto)
                .collect(Collectors.toList());
        return exerciseDtoList;
    }

    public ExerciseDto update(Long id, Long catalogId ) {
        Exercise exercise=exerciseRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Exercise not found"));
        ExerciseCatalog exerciseCatalog=exerciseCatalogRepository.findById(catalogId)
                .orElseThrow(()-> new RuntimeException("Exercise catalog not found"));
        exercise.setExerciseCatalog(exerciseCatalog);
        Exercise exerciseUpdate=exerciseRepository.save(exercise);
        return exerciseMapper.toDto(exerciseUpdate);
    }

    public ExerciseDto update (Long id, ExerciseDto exerciseDto)  {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Exercise not found"));
        if(exercise instanceof StrengthExercise strengthExercise) {
                if (exerciseDto.getSets() != null)
                    strengthExercise.setSets(exerciseDto.getSets());
                if (exerciseDto.getReps() != null)
                    strengthExercise.setReps(exerciseDto.getReps());
                if (exerciseDto.getWeight() != null)
                    strengthExercise.setWeight(exerciseDto.getWeight());
                Exercise exerciseUpdate = exerciseRepository.save(exercise);
                return exerciseMapper.toDto(exerciseUpdate);
        }else if(exercise instanceof CardioExercise cardioExercise){
            if(cardioExercise.getDuration() != null){
                cardioExercise.setDuration(exerciseDto.getDuration());
            }
        }
       return null;
    }

    public void delete (Long id){
        exerciseRepository.deleteById(id);
    }
}
