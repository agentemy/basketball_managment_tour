package com.example.dto.request;

public record UpdatePlayerStatsRequest(
        Integer points,
        Integer fouls,
        Integer assists
) {}