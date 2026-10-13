package com.example.openhackFreePalestine.repositories;

import com.example.openhackFreePalestine.entity.BankQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankQueueRepository extends JpaRepository<BankQueue, Long> {
}
