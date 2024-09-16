package com.example.demo.services;

import com.example.demo.entity.Studio;
import com.example.demo.repository.StudioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudioService {

    private final StudioRepository studioRepository;

    @Transactional
    public Studio findOrSaveStudio(String studioName) {
        Optional<Studio> movieProducer = studioRepository.findFirstByNameIgnoringCaseContaining(studioName);
        return movieProducer.orElseGet(()
                -> studioRepository.save(new Studio(null, studioName)));
    }
}
