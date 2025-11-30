package com.example.controller;



import com.example.dto.request.RegistrationPlayerRequest;
import com.example.dto.response.RegistrationPlayerResponse;
import com.example.entity.Player;
import com.example.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;


    @PostMapping
    public RegistrationPlayerResponse registerPlayer(@RequestBody RegistrationPlayerRequest request) {

        return playerService.registerPlayer(request);
    }

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @DeleteMapping("/delete/{id}")
    public void deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
    }

}

