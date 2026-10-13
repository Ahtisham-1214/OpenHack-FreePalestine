package com.example.openhackFreePalestine.service;

import com.example.openhackFreePalestine.dtos.UserRegistrationDto;
import com.example.openhackFreePalestine.entity.User;
import com.example.openhackFreePalestine.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User registerUser(UserRegistrationDto registrationDto) {
        // 1. Check if username is already taken
        if (userRepository.findByUsername(registrationDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken!");
        }

        // 2. Map DTO to Entity
        User newUser = new User();
        newUser.setUsername(registrationDto.getUsername());

        // 3. IMPORTANT: Hash the password before saving! Never store raw passwords.
        newUser.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        // 4. Assign default role
        newUser.setRole("ADMIN");

        // 5. Save to MySQL
        return userRepository.save(newUser);
    }
}
