package com.example.openhackFreePalestine.service;

import com.example.openhackFreePalestine.dtos.JobRequestDto;
import com.example.openhackFreePalestine.dtos.JobResponseDto;
import com.example.openhackFreePalestine.exception.ResourceNotFoundException;
import com.example.openhackFreePalestine.mapper.MyMapper;
import com.example.openhackFreePalestine.repositories.JobAndTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobAndTimeRepository jobAndTimeRepository;
    private final MyMapper myMapper;
    @Autowired
    public JobService(JobAndTimeRepository jobAndTimeRepository, MyMapper myMapper){
        this.jobAndTimeRepository = jobAndTimeRepository;
        this.myMapper = myMapper;
    }

    public JobResponseDto postJobAndTime(JobRequestDto jobRequestDto) {
        return myMapper.toJobAndTimeResponseDto(
                jobAndTimeRepository.save(
                        myMapper.toJobAndTime(jobRequestDto)));
    }


    public void deleteJobAndTime(Long jobId) {
        if (!jobAndTimeRepository.existsById(jobId))
            throw new ResourceNotFoundException("Job and time with id " + jobId + " does not exist", JobService.class.getSimpleName());

        jobAndTimeRepository.deleteById(jobId);


    }


    public List<JobResponseDto> getJobAndTimes() {
        return jobAndTimeRepository.findAll().stream()
                .map(myMapper::toJobAndTimeResponseDto)
                .toList();
    }
}
