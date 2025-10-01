package com.example.Gymify.service;

import com.example.Gymify.model.dto.UserDto;
import java.util.List;
import java.util.Optional;


public interface UserService {

    UserDto save(UserDto userDto);

     Optional<UserDto> findById(Long id);

     List<UserDto> findAll();

     UserDto update(Long userId, Long imageId);

     void delete(Long id);
}

