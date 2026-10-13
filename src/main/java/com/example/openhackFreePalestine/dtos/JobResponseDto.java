package com.example.openhackFreePalestine.dtos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobResponseDto {
    private Long id;
    private String job;
    private double time;
}
