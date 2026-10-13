package com.example.openhackFreePalestine.mapper;


import com.example.openhackFreePalestine.dtos.JobRequestDto;
import com.example.openhackFreePalestine.dtos.JobResponseDto;
import com.example.openhackFreePalestine.entity.Job;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MyMapper {

    Job toJobAndTime(JobRequestDto jobRequestDto);

    JobResponseDto toJobAndTimeResponseDto(Job job);

}
