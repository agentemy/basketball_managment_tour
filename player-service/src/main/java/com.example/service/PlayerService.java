package com.example.service;



import com.example.dto.request.RegistrationPlayerRequest;
import com.example.dto.response.RegistrationPlayerResponse;
import com.example.entity.Player;
import com.example.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public RegistrationPlayerResponse registerPlayer(RegistrationPlayerRequest request){
        Player player = Player.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .jerseyNumber(request.jerseyNumber())
                .gender(request.gender())
                .position(request.position())
                .playerStatus(request.playerStatus())
                .build();

        Player saved_player = playerRepository.save(player);

        return new RegistrationPlayerResponse(
                request.firstname(),
                request.lastname(),
                request.jerseyNumber(),
                request.gender(),
                request.position(),
                request.playerStatus(),
                saved_player.getId()
                );

        
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public void deletePlayer(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));
        playerRepository.delete(player);
    }

}