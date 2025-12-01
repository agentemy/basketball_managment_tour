package com.match.dto.request;

public record UpdatePlayerStatsRequest(
        Long playerId,
        Long teamId,
        Integer points,
        Integer fouls,
        Integer assists
) {}