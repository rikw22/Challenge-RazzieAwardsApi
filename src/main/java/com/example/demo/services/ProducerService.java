package com.example.demo.services;

import com.example.demo.entity.Producer;
import com.example.demo.repository.ProducerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProducerService {

    private final ProducerRepository producerRepository;

    @Transactional
    public Producer findOrSaveProducer(String producerName) {
        Optional<Producer> movieProducer = producerRepository.findFirstByNameIgnoringCaseContaining(producerName);
        return movieProducer.orElseGet(()
                -> producerRepository.save(new Producer(null, producerName)));
    }
}
