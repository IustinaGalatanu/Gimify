package com.example.Gymify.service;

import com.example.Gymify.model.User;
import com.example.Gymify.model.dto.UserDto;
import com.example.Gymify.repository.UserRepository;
import com.example.Gymify.service.mapper.UserMappper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMappper userMappper;

    public UserService(UserRepository userRepository, UserMappper userMappper){
        this.userRepository = userRepository;
        this.userMappper = userMappper;
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
        List<User> listUser= userRepository.findAll();
        List<UserDto> listUserDto = listUser.stream()
                .map(userMappper::toDto)
                .collect(Collectors.toList());
        return listUserDto;
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
