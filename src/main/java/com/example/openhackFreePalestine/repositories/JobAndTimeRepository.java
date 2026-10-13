package com.example.openhackFreePalestine.repositories;

import com.example.openhackFreePalestine.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobAndTimeRepository extends JpaRepository<Job, Long> {

}
