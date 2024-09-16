package com.example.demo.controller;

import com.example.demo.dto.ProducersStatsResponse;
import com.example.demo.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producer")
@RequiredArgsConstructor
public class ProducersController {

    private final MovieService movieService;

    @GetMapping(value ="/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProducersStatsResponse> stats(){

        ProducersStatsResponse response = movieService.stats();
        return ResponseEntity.ok(response);
    }

}
