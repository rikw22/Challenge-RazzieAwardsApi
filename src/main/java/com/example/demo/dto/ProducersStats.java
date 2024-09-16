package com.example.demo.dto;

import lombok.Builder;

@Builder
public record ProducersStats(
        String producer,
        Integer interval,
        Integer previousWin,
        Integer followingWin
) {
}
