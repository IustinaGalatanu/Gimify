package com.example.Gymify.service;

import com.example.Gymify.model.Exercise;
import com.example.Gymify.model.ExerciseType;
import com.example.Gymify.model.Workout;
import com.example.Gymify.model.dto.ExerciseDto;
import com.example.Gymify.repository.ExerciseRepository;
import com.example.Gymify.repository.ExerciseTypeRepository;
import com.example.Gymify.repository.WorkoutRepository;
import com.example.Gymify.service.mapper.ExerciseMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;
    private final WorkoutRepository workoutRepository;
    private final ExerciseTypeRepository exerciseTypeRepository;


    public ExerciseService(ExerciseRepository exerciseRepository, ExerciseMapper exerciseMapper, WorkoutRepository workoutRepository, ExerciseTypeRepository exerciseTypeRepository){
        this.exerciseRepository=exerciseRepository;
        this.exerciseMapper = exerciseMapper;
        this.workoutRepository = workoutRepository;
        this.exerciseTypeRepository = exerciseTypeRepository;
    }

    public ExerciseDto save(ExerciseDto exerciseDto){
        Workout workout = workoutRepository.findById(exerciseDto.getWorkoutId())
                .orElseThrow(() -> new RuntimeException("Workout not found"));
        ExerciseType type=exerciseTypeRepository.findById(exerciseDto.getExerciseTypeId())
                .orElseThrow(()-> new RuntimeException("Exercise Type not found"));
        Exercise exercise=exerciseMapper.createFromDto(exerciseDto,workout,type);
        Exercise savedExercise=exerciseRepository.save(exercise);
        return exerciseMapper.toDto(savedExercise);
    }


    public Optional<ExerciseDto> findById(Long id){
        Optional<Exercise> optionalExercise=exerciseRepository.findById(id);
        Optional<ExerciseDto> optionalExerciseDto=optionalExercise.map(exerciseMapper::toDto);
        return optionalExerciseDto;
    }

    public List<ExerciseDto> findAll(){
        List<Exercise> exerciseList=exerciseRepository.findAll();
        List<ExerciseDto> exerciseDtoList=exerciseList.stream()
                .map(exerciseMapper::toDto)
                .collect(Collectors.toList());
        return exerciseDtoList;
    }

    public List<ExerciseDto> findExerciseByWorkoutId(Long id) {
        List<Exercise> exerciseList=exerciseRepository.findExerciseByWorkoutId(id);
        List<ExerciseDto> exerciseDtoList=exerciseList.stream()
                .map(exerciseMapper::toDto)
                .collect(Collectors.toList());
        return exerciseDtoList;
    }

    public ExerciseDto updateType(Long id,Long exerciseTypeId ) {
        Exercise exercise=exerciseRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Exercise not found"));
        ExerciseType exerciseType=exerciseTypeRepository.findById(exerciseTypeId)
                .orElseThrow(()-> new RuntimeException("Exercise Type not found"));
        exercise.setExerciseType(exerciseType);
        Exercise exerciseUpdate=exerciseRepository.save(exercise);
        return exerciseMapper.toDto(exerciseUpdate);
    }

    public ExerciseDto update (Long id, ExerciseDto exerciseDto){
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Exercise not found"));
        if(exerciseDto.getSets()!= null)
            exercise.setSets(exerciseDto.getSets());
        if(exerciseDto.getRep() != null)
            exercise.setRep(exerciseDto.getRep());
        if(exerciseDto.getWeight() != null)
            exercise.setWeight(exerciseDto.getWeight());
        Exercise exerciseUpdate=exerciseRepository.save(exercise);
        return exerciseMapper.toDto(exerciseUpdate);
    }

    public void delete (Long id){
        exerciseRepository.deleteById(id);
    }
}
