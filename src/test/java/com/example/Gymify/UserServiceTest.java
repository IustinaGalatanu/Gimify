package com.example.Gymify;

import com.example.Gymify.model.Image;
import com.example.Gymify.model.User;
import com.example.Gymify.model.dto.UserDto;
import com.example.Gymify.repository.ImageRepository;
import com.example.Gymify.repository.UserRepository;
import com.example.Gymify.service.implementation.UserServiceImpl;
import com.example.Gymify.service.mapper.UserMappper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private UserMappper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDto userDto;
    private Image image;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        image = new Image();
        image.setId(1L);
        image.setUrl("profile.jpg");

        user = new User();
        user.setId(1L);
        user.setName("Iustina");
        user.setEmail("iustinagalatanu@gmail.com");
        user.setGoal("Strength");
        user.setImage(image);

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Iustina");
        userDto.setEmail("iustinagalatanu@gmail.com");
        userDto.setGoal("Strength");
    }

    // ✅ save()
    @Test
    void save_ShouldReturnSavedUserDto() {
        when(userMapper.createFromDto(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.save(userDto);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("iustinagalatanu@gmail.com");
        verify(userRepository, times(1)).save(user);
    }

    // ✅ findById() - found
    @Test
    void findById_ShouldReturnUser_WhenExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        Optional<UserDto> result = userService.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Iustina");
        verify(userRepository, times(1)).findById(1L);
    }

    // ✅ findById() - not found
    @Test
    void findById_ShouldReturnEmpty_WhenNotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<UserDto> result = userService.findById(2L);

        assertThat(result).isEmpty();
    }

    // ✅ findAll()
    @Test
    void findAll_ShouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        List<UserDto> result = userService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getEmail()).isEqualTo("iustinagalatanu@gmail.com");
        verify(userRepository, times(1)).findAll();
    }

    // ✅ update() - image
    @Test
    void update_ShouldUpdateUserImage_WhenExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(imageRepository.findById(1L)).thenReturn(Optional.of(image));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.update(1L, 1L);

        assertThat(result).isNotNull();
        verify(userRepository).save(user);
    }

    // ❌ update() - image not found
    @Test
    void update_ShouldThrowException_WhenImageNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(imageRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.update(1L, 99L));
    }

    // ✅ updateGoal()
    @Test
    void updateGoal_ShouldChangeGoalSuccessfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.updateGoal(1L, "Cardio");

        assertThat(result).isNotNull();
        verify(userRepository).save(user);
        assertThat(user.getGoal()).isEqualTo("Cardio");
    }

    // ❌ updateGoal() - user not found
    @Test
    void updateGoal_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.updateGoal(99L, "Cardio"));
    }

    // ✅ delete()
    @Test
    void delete_ShouldCallRepository() {
        userService.delete(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
