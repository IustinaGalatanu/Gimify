package com.example.Gymify.controller;

import com.example.Gymify.model.dto.UserDto;
import com.example.Gymify.service.implementation.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    private final UserServiceImpl userService;


    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserDto savedUserDto = userService.save(userDto);
        return ResponseEntity.ok(savedUserDto);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> allUsersDto = userService.findAll();
        return ResponseEntity.ok(allUsersDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserDto>> getUserById(@PathVariable Long id){
        Optional<UserDto> userByIdToDto = userService.findById(id);
        return ResponseEntity.ok(userByIdToDto);
    }

    @PatchMapping("{id}/image")
    public ResponseEntity<UserDto> updateImageForUser(@PathVariable Long id, @RequestParam Long imageId) {
        UserDto userDtoUpdate=userService.update(id,imageId);
        return ResponseEntity.ok(userDtoUpdate);
    }

    @PatchMapping("{id}/goal")
    public ResponseEntity<UserDto> updateGoalForUser(@PathVariable Long id, @RequestParam String goal) {
        UserDto userDtoUpdate=userService.updateGoal(id,goal);
        return ResponseEntity.ok(userDtoUpdate);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
