package com.example.dto.response;

import com.example.entity.TeamCategory;
import com.example.entity.TeamStatus;

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
