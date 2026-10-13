package com.example.openhackFreePalestine.controller;

import com.example.openhackFreePalestine.dtos.ServiceRequestDto;
import com.example.openhackFreePalestine.dtos.ServiceResponseDto;
import com.example.openhackFreePalestine.entity.BankQueue;
import com.example.openhackFreePalestine.entity.TokenStatus;
import com.example.openhackFreePalestine.repositories.BankQueueRepository;
import com.example.openhackFreePalestine.service.BankQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/services")
public class BankQueueController {

    private final BankQueueService bankQueueService;

    private final BankQueueRepository bankQueueRepository;

    @Autowired
    public BankQueueController(BankQueueService bankQueueService, BankQueueRepository bankQueueRepository) {
        this.bankQueueService = bankQueueService;
        this.bankQueueRepository = bankQueueRepository;
    }

    @PostMapping
    public ResponseEntity<ServiceResponseDto> requestService(@RequestBody ServiceRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bankQueueService.requestService(request));
    }

    // Add these methods to your BankQueueController or a dedicated AdminController

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateTokenStatus(@PathVariable Long id, @RequestParam TokenStatus status) {
        BankQueue token = bankQueueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        token.setStatus(status);
        if (status == TokenStatus.COMPLETED) {
            token.setCompletedAt(LocalDateTime.now());
        }
        bankQueueRepository.save(token);
        return ResponseEntity.ok(Map.of("message", "Token status updated to " + status));
    }

    // Add to BankQueueController.java
    @GetMapping("/all")
    public ResponseEntity<List<BankQueue>> getAllTokens() {
        return ResponseEntity.ok(bankQueueRepository.findAll());
    }

    @GetMapping("/analytics")
    public ResponseEntity<?> getQueueAnalytics() {
        long completed = bankQueueRepository.countByStatus(TokenStatus.COMPLETED);
        long waiting = bankQueueRepository.countByStatus(TokenStatus.WAITING);
        long serving = bankQueueRepository.countByStatus(TokenStatus.SERVING);
        long cancelled = bankQueueRepository.countByStatus(TokenStatus.CANCELLED);

        return ResponseEntity.ok(Map.of(
                "completedTokens", completed,
                "activeQueueLength", waiting,
                "currentlyServing", serving,
                "cancelledTokens", cancelled
        ));
    }


    @GetMapping("/status/{tokenId}")
    public ResponseEntity<?> getTokenStatus(@PathVariable Long tokenId) {
        BankQueue userToken = bankQueueRepository.findById(tokenId)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        if (userToken.getStatus() != TokenStatus.WAITING) {
            return ResponseEntity.ok(Map.of("status", userToken.getStatus()));
        }

        // Get the optimized queue order
        List<BankQueue> queue = bankQueueService.getOptimizedQueue();

        int position = 0;
        double estimatedWaitTime = 0.0;
        boolean found = false;

        for (BankQueue token : queue) {
            position++;
            if (token.getId().equals(tokenId)) {
                found = true;
                break;
            }
            // Accumulate service times of people ahead of them
            estimatedWaitTime += token.getEstimatedServiceTimeMinutes();
        }

        if (!found) {
            position = -1; // Already being served or completed
        }

        return ResponseEntity.ok(Map.of(
                "tokenId", userToken.getId(),
                "service", userToken.getService(),
                "positionInQueue", position,
                "estimatedWaitMinutes", estimatedWaitTime,
                "status", userToken.getStatus()
        ));
    }

}
