package com.example.openhackFreePalestine.repositories;

import com.example.openhackFreePalestine.entity.JobAndTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface JobAndTimeRepositories extends JpaRepository<JobAndTime, Long> {

}
