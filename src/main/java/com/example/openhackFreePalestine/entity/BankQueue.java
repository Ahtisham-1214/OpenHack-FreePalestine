package com.example.openhackFreePalestine.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "queue_tokens")
public class BankQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // This serves as your Token Number!

    private String name;
    private String service;

    // New fields for queue management
    private Integer estimatedServiceTimeMinutes; // Duration for this specific service task

    @Enumerated(EnumType.STRING)
    private TokenStatus status = TokenStatus.WAITING; // WAITING, SERVING, COMPLETED, CANCELLED

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime completedAt;
}
