package com.example.demo.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ProducersStatsResponse (
    List<ProducersStats> min,
    List<ProducersStats> max
){}
