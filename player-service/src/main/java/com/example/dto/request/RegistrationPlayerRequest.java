package com.example.dto.request;


import com.example.entity.PlayerStatus;
import com.example.entity.Position;

public record RegistrationPlayerRequest(
        String firstname,
        String lastname,
        String jerseyNumber,
        String gender,
        Position position,
        PlayerStatus playerStatus
){
}
