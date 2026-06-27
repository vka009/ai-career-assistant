package com.vignesh.backend.controller;

import com.vignesh.backend.dto.request.LoginRequest;
import com.vignesh.backend.dto.response.LoginResponse;
import com.vignesh.backend.dto.request.RegisterRequest;
import com.vignesh.backend.dto.response.RegisterResponse;
import com.vignesh.backend.entity.User;
import com.vignesh.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        return ResponseEntity.ok(userService.login(request));

    }
}