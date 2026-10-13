package com.example.openhackFreePalestine.service;

import com.example.openhackFreePalestine.dtos.ServiceRequestDto;
import com.example.openhackFreePalestine.dtos.ServiceResponseDto;
import com.example.openhackFreePalestine.mapper.MyMapper;
import com.example.openhackFreePalestine.repositories.BankQueueRepository;
import com.example.openhackFreePalestine.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankQueueService {
    private final BankQueueRepository bankQueueRepository;
    private final JobRepository jobRepository;
    private final MyMapper myMapper;

    @Autowired
    public BankQueueService(BankQueueRepository bankQueueRepository, JobRepository jobRepository, MyMapper myMapper) {
        this.bankQueueRepository = bankQueueRepository;
        this.jobRepository = jobRepository;
        this.myMapper = myMapper;
    }

    public ServiceResponseDto requestService(ServiceRequestDto request) {
        if (!jobRepository.existsByJob(request.getService()))
                throw new RuntimeException("Selected Service Bank does not Offer");

            return myMapper.toServiceResponseDto(
                        bankQueueRepository.save(myMapper.toBankQueue(request))
                );


    }
}
