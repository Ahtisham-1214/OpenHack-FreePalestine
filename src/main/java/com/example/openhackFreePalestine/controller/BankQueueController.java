package com.example.openhackFreePalestine.controller;

import com.example.openhackFreePalestine.dtos.ServiceRequestDto;
import com.example.openhackFreePalestine.dtos.ServiceResponseDto;
import com.example.openhackFreePalestine.service.BankQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
public class BankQueueController {

    private final BankQueueService bankQueueService;

    @Autowired
    public BankQueueController(BankQueueService bankQueueService) {
        this.bankQueueService = bankQueueService;
    }

    @PostMapping
    public ResponseEntity<ServiceResponseDto> requestService(@RequestBody ServiceRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bankQueueService.requestService(request));
    }


}
