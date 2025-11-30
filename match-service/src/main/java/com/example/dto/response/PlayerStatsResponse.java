package com.example.dto.response;

public record PlayerStatsResponse(
        Long playerId,
        Integer points,
        Integer fouls,
        Integer assists
) {}