package com.match.dto.response;

import com.match.entity.TeamCategory;
import com.match.entity.TeamStatus;

import java.util.List;

public record TeamResponse (
        Long id,
        String name,
        TeamStatus status,
        TeamCategory category,
        List<Long> playersIds,
        Long captainId,
        Integer wins,
        Integer losses
)
{
}
