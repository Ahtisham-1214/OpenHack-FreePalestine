package com.example.openhackFreePalestine.service;

import com.example.openhackFreePalestine.dtos.ServiceRequestDto;
import com.example.openhackFreePalestine.dtos.ServiceResponseDto;
import com.example.openhackFreePalestine.entity.BankQueue;
import com.example.openhackFreePalestine.entity.Job;
import com.example.openhackFreePalestine.entity.TokenStatus;
import com.example.openhackFreePalestine.mapper.MyMapper;
import com.example.openhackFreePalestine.repositories.BankQueueRepository;
import com.example.openhackFreePalestine.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

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
        Job job = jobRepository.findByJob(request.getService())
                .orElseThrow(() -> new IllegalArgumentException("Selected Service does not exist"));

        BankQueue queueItem = myMapper.toBankQueue(request);
        queueItem.setStatus(TokenStatus.WAITING);
        queueItem.setCreatedAt(LocalDateTime.now());

        // Store the official duration from the Job table into your queue entity
        queueItem.setEstimatedServiceTimeMinutes((int) job.getTime());

        BankQueue saved = bankQueueRepository.save(queueItem);
        return myMapper.toServiceResponseDto(saved);
    }

    /**
     * Shortest Job First (SJF) with Aging Factor:
     * Priority Score = ServiceTime - (WaitTime * AgingWeight)
     * Lower score means higher priority in the queue.
     */
    public List<BankQueue> getOptimizedQueue() {
        List<BankQueue> waitingTokens = bankQueueRepository.findByStatus(TokenStatus.WAITING);
        LocalDateTime now = LocalDateTime.now();

        PriorityQueue<BankQueue> pq = new PriorityQueue<>(Comparator.comparingDouble(token -> {
            long waitMinutes = Duration.between(token.getCreatedAt(), now).toMinutes();
            double serviceTime = token.getEstimatedServiceTimeMinutes();

            // Aging factor: 0.2 weight multiplier ensures waiting tasks gradually rise up
            return serviceTime - (waitMinutes * 0.2);
        }));

        pq.addAll(waitingTokens);
        return pq.stream().toList();
    }
}