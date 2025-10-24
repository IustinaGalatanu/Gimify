package com.example.Gymify.controller;

import com.example.Gymify.model.User;
import com.example.Gymify.model.dto.UserDto;
import com.example.Gymify.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String SECRET_KEY = "super_secret_123456";

    // 🔹 Register
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setGoal(dto.getGoal());

        // criptare parolă înainte de salvare
        String encoded = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encoded);

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    // 🔹 Login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto dto) {
        Optional<User> userOpt = userRepository.findByEmail(dto.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        User user = userOpt.get();
        // verificăm parola (hash vs hash)
        boolean matches = passwordEncoder.matches(dto.getPassword(), user.getPassword());
        if (!matches) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        // generăm token JWT simplu
        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 ore
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();

        return ResponseEntity.ok(token);
    }
}
