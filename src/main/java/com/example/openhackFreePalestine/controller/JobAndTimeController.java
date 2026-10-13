package com.example.openhackFreePalestine.controller;


import com.example.openhackFreePalestine.dtos.JobAndTimeRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/jobs")
public class JobAndTimeController {

    @PostMapping
    public void postJobAndTime(@RequestBody JobAndTimeRequestDto jobAndTimeRequestDto) {


    }

}
