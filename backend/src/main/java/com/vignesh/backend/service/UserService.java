package com.vignesh.backend.service;

import com.vignesh.backend.entity.User;
import com.vignesh.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}