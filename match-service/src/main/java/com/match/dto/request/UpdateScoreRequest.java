package com.match.dto.request;

public record UpdateScoreRequest(
        Integer homeTeamScore,
        Integer awayTeamScore
) {}