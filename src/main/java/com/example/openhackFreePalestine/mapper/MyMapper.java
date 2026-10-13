package com.example.openhackFreePalestine.mapper;


import com.example.openhackFreePalestine.dtos.JobRequestDto;
import com.example.openhackFreePalestine.dtos.JobResponseDto;
import com.example.openhackFreePalestine.dtos.ServiceRequestDto;
import com.example.openhackFreePalestine.dtos.ServiceResponseDto;
import com.example.openhackFreePalestine.entity.BankQueue;
import com.example.openhackFreePalestine.entity.Job;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MyMapper {

    Job toJobAndTime(JobRequestDto jobRequestDto);

    JobResponseDto toJobAndTimeResponseDto(Job job);

    ServiceResponseDto toServiceResponseDto(BankQueue bankQueue);

    BankQueue toBankQueue(ServiceRequestDto serviceRequestDto);
}
