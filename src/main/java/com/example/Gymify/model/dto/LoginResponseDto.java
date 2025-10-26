package com.example.Gymify.model.dto;

import lombok.Data;

@Data
public class LoginResponseDto {

    private String token;
    private Long userId;

    public LoginResponseDto(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }
}
