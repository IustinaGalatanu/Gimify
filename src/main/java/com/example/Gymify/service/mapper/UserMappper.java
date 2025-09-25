package com.example.Gymify.service.mapper;

import com.example.Gymify.model.Image;
import com.example.Gymify.model.User;
import com.example.Gymify.model.Workout;
import com.example.Gymify.model.dto.UserDto;
import com.example.Gymify.model.dto.WorkoutSummaryDto;
import com.example.Gymify.repository.ImageRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMappper {

    private final ImageRepository imageRepository;

    public UserMappper(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public User createFromDto(UserDto userDto){
        User user=new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setGoal(userDto.getGoal());
        user.setWorkouts(workoutsSummaryFromDto(userDto.getWorkouts(),user));
        user.setImage(imageFromDto(userDto.getImageId()));
        return user;
    }

    private Image imageFromDto (Long imageIdDto){
        if(imageIdDto !=null){
            Image image=imageRepository.findById(imageIdDto)
                    .orElseThrow(()->new RuntimeException("Image not found"));
            return image;
        }
        return null;
    }

    private List<Workout> workoutsSummaryFromDto(List<WorkoutSummaryDto> workoutSummaryDtos, User user){
        // List<Workout> list = null
        // List<Workout> list = List.of(); -> []
        if(workoutSummaryDtos !=null && !workoutSummaryDtos.isEmpty()){
            List<Workout> workouts= workoutSummaryDtos.stream()
                    // List<Workout> list = List.of(); -> [null, "squats", null]
                    .filter(workoutSummaryDto -> workoutSummaryDto !=null )
                    .map(workoutSummaryDto -> workoutSummaryFromDto(workoutSummaryDto,user))
                    .collect(Collectors.toList());

            return workouts;
        }
        return List.of();
    }

    private Workout workoutSummaryFromDto(WorkoutSummaryDto workoutSummaryDto, User user){
        Workout workout =new Workout();
        workout.setId(workoutSummaryDto.getId());
        workout.setName(workoutSummaryDto.getName());
        workout.setCreateTimestamp(workoutSummaryDto.getCreateTimestamp());
        workout.setUser(user);
        return workout;
    }

    public UserDto toDto(User user){
        UserDto userDto =new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword("****");
        userDto.setGoal(user.getGoal());
        userDto.setWorkouts(workoutSummaryDtosToDtos(user.getWorkouts(),userDto));
        if(user.getImage()!=null) {
            userDto.setImageId(user.getImage().getId());
        }
        return userDto;
    }


    private List<WorkoutSummaryDto> workoutSummaryDtosToDtos(List<Workout> workouts, UserDto userDto){
        if(workouts!=null){
            List<WorkoutSummaryDto> workoutSummaryDtos =workouts.stream()
                    .map(workout ->{
                        WorkoutSummaryDto workoutSummaryDto =new WorkoutSummaryDto();
                        workoutSummaryDto.setId(workout.getId());
                        workoutSummaryDto.setName(workout.getName());
                        workoutSummaryDto.setCreateTimestamp(workout.getCreateTimestamp());

                        return workoutSummaryDto;
                    })
                    .collect(Collectors.toList());
            return workoutSummaryDtos;
        }
        return List.of();
    }
}
