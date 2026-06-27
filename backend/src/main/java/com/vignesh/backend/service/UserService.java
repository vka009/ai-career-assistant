package com.vignesh.backend.service;

import com.vignesh.backend.dto.request.LoginRequest;
import com.vignesh.backend.dto.response.LoginResponse;
import com.vignesh.backend.entity.User;
import com.vignesh.backend.exception.InvalidCredentialsException;
import com.vignesh.backend.repository.UserRepository;
import com.vignesh.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.vignesh.backend.dto.request.RegisterRequest;
import com.vignesh.backend.dto.response.RegisterResponse;
import com.vignesh.backend.mapper.UserMapper;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.vignesh.backend.exception.DuplicateEmailException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Email already exists");
        }

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        log.info("User registered successfully with email: {}", savedUser.getEmail());

        return userMapper.toResponse(savedUser);
    }

    public User saveUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->{
                    log.warn("Failed login attempt for email: {}", request.getEmail());
                    return new InvalidCredentialsException("Invalid email or password");
                });

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("Failed login attempt for email: {}", request.getEmail());
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());
        log.info("User logged in successfully: {}", user.getEmail());
        return new LoginResponse(token, "Login successful");
    }
}