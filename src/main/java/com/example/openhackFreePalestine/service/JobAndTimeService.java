package com.example.openhackFreePalestine.service;

import com.example.openhackFreePalestine.dtos.JobAndTimeRequestDto;
import com.example.openhackFreePalestine.dtos.JobAndTimeResponseDto;
import com.example.openhackFreePalestine.mapper.MyMapper;
import com.example.openhackFreePalestine.repositories.JobAndTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobAndTimeService {

    private final JobAndTimeRepository jobAndTimeRepository;
    private final MyMapper myMapper;
    @Autowired
    public JobAndTimeService(JobAndTimeRepository jobAndTimeRepository, MyMapper myMapper){
        this.jobAndTimeRepository = jobAndTimeRepository;
        this.myMapper = myMapper;
    }

    public JobAndTimeResponseDto postJobAndTime(JobAndTimeRequestDto jobAndTimeRequestDto) {
        return myMapper.toJobAndTimeResponseDto(
                jobAndTimeRepository.save(
                        myMapper.toJobAndTime(jobAndTimeRequestDto)));
    }
}
