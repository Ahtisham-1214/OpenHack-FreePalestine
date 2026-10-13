package com.example.openhackFreePalestine.repositories;

import com.example.openhackFreePalestine.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    boolean existsByJob(String job);

    Optional<Job> findByJob(String job);
}
