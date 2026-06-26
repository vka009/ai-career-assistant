package com.vignesh.backend.controller;

import com.vignesh.backend.dto.LoginRequest;
import com.vignesh.backend.dto.LoginResponse;
import com.vignesh.backend.entity.User;
import com.vignesh.backend.service.UserService;
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
    public User register(@RequestBody User user) {
        System.out.println("******** REGISTER API HIT ********");
        return userService.saveUser(user);
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