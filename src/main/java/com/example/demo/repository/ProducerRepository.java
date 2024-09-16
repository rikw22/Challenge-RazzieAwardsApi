package com.example.demo.repository;

import com.example.demo.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProducerRepository extends JpaRepository<Producer, Integer> {

    Optional<Producer> findFirstByNameIgnoringCaseContaining(String producerName);

}
