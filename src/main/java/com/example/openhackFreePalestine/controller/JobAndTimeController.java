package com.example.openhackFreePalestine.controller;


import com.example.openhackFreePalestine.dtos.JobAndTimeRequestDto;
import com.example.openhackFreePalestine.dtos.JobAndTimeResponseDto;
import com.example.openhackFreePalestine.service.JobAndTimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
public class JobAndTimeController {

    private final JobAndTimeService jobAndTimeService;

    public JobAndTimeController(JobAndTimeService jobAndTimeService) {
        this.jobAndTimeService = jobAndTimeService;
    }

    @PostMapping
    public ResponseEntity<JobAndTimeResponseDto> postJobAndTime(@RequestBody JobAndTimeRequestDto jobAndTimeRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobAndTimeService.postJobAndTime(jobAndTimeRequestDto));

    }

}
