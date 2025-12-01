package com.match.dto.request;

import com.match.entity.MatchType;

import java.time.LocalDateTime;

public record CreateMatchRequest(
        Long homeTeamId,
        Long awayTeamId,
        LocalDateTime dateTime,
        MatchType type
) {}
