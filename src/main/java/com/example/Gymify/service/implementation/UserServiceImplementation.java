package com.example.Gymify.service.implementation;

import com.example.Gymify.model.Image;
import com.example.Gymify.model.User;
import com.example.Gymify.model.dto.UserDto;
import com.example.Gymify.repository.ImageRepository;
import com.example.Gymify.repository.UserRepository;
import com.example.Gymify.service.UserService;
import com.example.Gymify.service.mapper.UserMappper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final UserMappper userMappper;
    private final ImageRepository imageRepository;

    public UserServiceImplementation(UserRepository userRepository, UserMappper userMappper, ImageRepository imageRepository){
        this.userRepository = userRepository;
        this.userMappper = userMappper;
        this.imageRepository = imageRepository;
    }

    public UserDto save(UserDto userDto) {
        User user = userMappper.createFromDto(userDto);
        User savedUser = userRepository.save(user);
        return userMappper.toDto(savedUser);
    }

    public Optional<UserDto> findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        Optional<UserDto> optionalUserDto = optionalUser.map(userMappper::toDto);
        return optionalUserDto;
    }

    public List<UserDto> findAll() {
        List<User> usersList = userRepository.findAll();
        List<UserDto> userDtoList = usersList.stream()
                .map(userMappper::toDto)
                .collect(Collectors.toList());
        return userDtoList;
    }

    public UserDto update(Long userId, Long imageId){
        User user=userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));
        Image image = imageRepository.findById(imageId)
                .orElseThrow(()-> new RuntimeException("Image not found"));
        user.setImage(image);
        User userSaved=userRepository.save(user);
        return userMappper.toDto(userSaved);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
