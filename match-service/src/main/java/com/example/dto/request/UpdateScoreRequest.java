package com.example.dto.request;

public record UpdateScoreRequest(
        Integer homeTeamScore,
        Integer awayTeamScore
) {}