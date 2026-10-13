package com.example.openhackFreePalestine.mapper;


import com.example.openhackFreePalestine.dtos.JobAndTimeRequestDto;
import com.example.openhackFreePalestine.dtos.JobAndTimeResponseDto;
import com.example.openhackFreePalestine.entity.JobAndTime;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MyMapper {

    JobAndTime toJobAndTime(JobAndTimeRequestDto jobAndTimeRequestDto);

    JobAndTimeResponseDto toJobAndTimeResponseDto(JobAndTime jobAndTime);

}
