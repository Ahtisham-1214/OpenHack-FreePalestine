package com.example.openhackFreePalestine.controller;


import com.example.openhackFreePalestine.dtos.JobAndTimeRequestDto;
import com.example.openhackFreePalestine.dtos.JobAndTimeResponseDto;
import com.example.openhackFreePalestine.service.JobAndTimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> postJobAndTime(@PathVariable Long jobId) {
        jobAndTimeService.deleteJobAndTime(jobId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @GetMapping
    public ResponseEntity<List<JobAndTimeResponseDto>> getJobAndTimes() {
        return ResponseEntity.ok(jobAndTimeService.getJobAndTimes());
    }


}
