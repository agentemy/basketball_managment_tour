package com.example.dto.response;


import com.example.entity.PlayerStatus;
import com.example.entity.Position;

public record RegistrationPlayerResponse(
        String firstname,
        String lastname,
        String jerseyNumber,
        String gender,
        Position position,
        PlayerStatus playerStatus,
        Long id
) {
}
