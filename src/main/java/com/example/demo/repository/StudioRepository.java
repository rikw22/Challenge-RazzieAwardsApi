package com.example.demo.repository;

import com.example.demo.entity.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudioRepository extends JpaRepository<Studio, Integer> {

    Optional<Studio> findFirstByNameIgnoringCaseContaining(String studioName);

}
