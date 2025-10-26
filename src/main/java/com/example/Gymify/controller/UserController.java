package com.example.Gymify.controller;

import com.example.Gymify.model.User;
import com.example.Gymify.model.dto.UserDto;
import com.example.Gymify.repository.UserRepository;
import com.example.Gymify.service.implementation.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
@SecurityRequirement(name="BearerAuth")
public class UserController {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;


    public UserController(UserServiceImpl userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }
//    @Operation(summary = "Create an user")
//    @PostMapping
//    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
//        UserDto savedUserDto = userService.save(userDto);
//        return ResponseEntity.ok(savedUserDto);
//    }
    @Operation(summary = "Get all users")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> allUsersDto = userService.findAll();
        return ResponseEntity.ok(allUsersDto);
    }
    @Operation(summary = "Get an user by id")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserDto>> getUserById(@PathVariable Long id){
        String currentEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByEmail(currentEmail).orElseThrow();
        if (!currentUser.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Optional<UserDto> userByIdToDto = userService.findById(id);
        return ResponseEntity.ok(userByIdToDto);
    }
    @Operation(summary = "Update an image for the user by id")
    @PatchMapping("{id}/image")
    public ResponseEntity<UserDto> updateImageForUser(@PathVariable Long id, @RequestParam Long imageId) {
        String currentEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByEmail(currentEmail).orElseThrow();
        if (!currentUser.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        UserDto userDtoUpdate=userService.update(id,imageId);
        return ResponseEntity.ok(userDtoUpdate);
    }
    @Operation(summary = "Update a goal for the user")
    @PatchMapping("{id}/goal")
    public ResponseEntity<UserDto> updateGoalForUser(@PathVariable Long id, @RequestParam String goal) {
        String currentEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByEmail(currentEmail).orElseThrow();
        if (!currentUser.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        UserDto userDtoUpdate=userService.updateGoal(id,goal);
        return ResponseEntity.ok(userDtoUpdate);
    }

    @Operation(summary = "Delete an user by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        String currentEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByEmail(currentEmail).orElseThrow();
        if (!currentUser.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
