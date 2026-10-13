package com.example.openhackFreePalestine.service;

import com.example.openhackFreePalestine.dtos.JobRequestDto;
import com.example.openhackFreePalestine.dtos.JobResponseDto;
import com.example.openhackFreePalestine.exception.ResourceNotFoundException;
import com.example.openhackFreePalestine.mapper.MyMapper;
import com.example.openhackFreePalestine.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final MyMapper myMapper;
    @Autowired
    public JobService(JobRepository jobRepository, MyMapper myMapper){
        this.jobRepository = jobRepository;
        this.myMapper = myMapper;
    }

    public JobResponseDto postJobAndTime(JobRequestDto jobRequestDto) {
        return myMapper.toJobAndTimeResponseDto(
                jobRepository.save(
                        myMapper.toJobAndTime(jobRequestDto)));
    }


    public void deleteJobAndTime(Long jobId) {
        if (!jobRepository.existsById(jobId))
            throw new ResourceNotFoundException("Job and time with id " + jobId + " does not exist", JobService.class.getSimpleName());

        jobRepository.deleteById(jobId);


    }


    public List<JobResponseDto> getJobAndTimes() {
        return jobRepository.findAll().stream()
                .map(myMapper::toJobAndTimeResponseDto)
                .toList();
    }
}
