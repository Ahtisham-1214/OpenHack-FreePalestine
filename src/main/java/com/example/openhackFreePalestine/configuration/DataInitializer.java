package com.example.openhackFreePalestine.configuration;

import com.example.openhackFreePalestine.entity.User;
import com.example.openhackFreePalestine.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin123").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin123");
                admin.setPassword(passwordEncoder.encode("admin123")); // Securely hash password
                admin.setRole("ADMIN");
                userRepository.save(admin);
                System.out.println("Default admin user created in database.");
            }
        };
    }
}
