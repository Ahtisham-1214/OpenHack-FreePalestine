package com.example.openhackFreePalestine.repositories;

import com.example.openhackFreePalestine.entity.BankQueue;
import com.example.openhackFreePalestine.entity.TokenStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankQueueRepository extends JpaRepository<BankQueue, Long> {

    int countByStatus(TokenStatus status);

    List<BankQueue> findByStatus(TokenStatus status);
}
