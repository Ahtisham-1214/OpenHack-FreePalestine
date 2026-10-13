package com.example.openhackFreePalestine.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // Will store the BCrypt hashed password

    @Column(nullable = false)
    private String role; // e.g., "ADMIN" or "USER"
}
