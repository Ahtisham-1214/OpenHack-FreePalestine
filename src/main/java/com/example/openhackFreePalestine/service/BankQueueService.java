package com.example.openhackFreePalestine.service;

import com.example.openhackFreePalestine.dtos.ServiceRequestDto;
import com.example.openhackFreePalestine.dtos.ServiceResponseDto;
import com.example.openhackFreePalestine.mapper.MyMapper;
import com.example.openhackFreePalestine.repositories.BankQueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankQueueService {
    private final BankQueueRepository bankQueueRepository;
    private final MyMapper myMapper;

    @Autowired
    public BankQueueService(BankQueueRepository bankQueueRepository, MyMapper myMapper) {
        this.bankQueueRepository = bankQueueRepository;
        this.myMapper = myMapper;
    }

    public ServiceResponseDto requestService(ServiceRequestDto request) {
       return myMapper.toServiceResponseDto(
               bankQueueRepository.save(myMapper.toBankQueue(request))
       );
    }
}
