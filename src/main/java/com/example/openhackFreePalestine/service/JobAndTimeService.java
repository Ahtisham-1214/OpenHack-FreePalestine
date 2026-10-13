package com.example.openhackFreePalestine.service;

import com.example.openhackFreePalestine.dtos.JobAndTimeRequestDto;
import com.example.openhackFreePalestine.dtos.JobAndTimeResponseDto;
import com.example.openhackFreePalestine.exception.ResourceNotFoundException;
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


    public void deleteJobAndTime(Long jobId) {
        if (!jobAndTimeRepository.existsById(jobId))
            throw new ResourceNotFoundException("Job and time with id " + jobId + " does not exist", JobAndTimeService.class.getSimpleName());

        jobAndTimeRepository.deleteById(jobId);


    }
}
