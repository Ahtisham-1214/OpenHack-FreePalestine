package com.example.openhackFreePalestine.controller;


import com.example.openhackFreePalestine.dtos.JobRequestDto;
import com.example.openhackFreePalestine.dtos.JobResponseDto;
import com.example.openhackFreePalestine.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<JobResponseDto> postJobAndTime(@RequestBody JobRequestDto jobRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.postJobAndTime(jobRequestDto));

    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> postJobAndTime(@PathVariable Long jobId) {
        jobService.deleteJobAndTime(jobId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @GetMapping
    public ResponseEntity<List<JobResponseDto>> getJobAndTimes() {
        return ResponseEntity.ok(jobService.getJobAndTimes());
    }


}
