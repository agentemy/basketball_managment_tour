package com.match.dto.response;

import com.match.entity.MatchStatus;
import com.match.entity.MatchType;

import java.time.LocalDateTime;

public record MatchResponse(
        Long id,
        Long homeTeamId,
        Long awayTeamId,
        LocalDateTime dateTime,
        MatchStatus status,
        MatchType type,
        Integer homeTeamScore,
        Integer awayTeamScore
) {}