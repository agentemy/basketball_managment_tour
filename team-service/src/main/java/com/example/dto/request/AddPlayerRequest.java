package com.example.dto.request;

public record AddPlayerRequest (
        Long teamId,
        Long playerId,
        Boolean isCaptain
)
{
}
