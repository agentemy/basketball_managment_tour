package com.example.controller;

import com.example.dto.request.TeamRequest;
import com.example.dto.request.AddPlayerRequest;
import com.example.dto.request.UpdateStatsRequest;
import com.example.dto.response.TeamResponse;
import com.example.entity.TeamCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {
    private final com.example.service.TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamResponse> createTeam(@RequestBody TeamRequest request) {
        return ResponseEntity.ok(teamService.createTeam(request));
    }

    @GetMapping
    public ResponseEntity<List<TeamResponse>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponse> getTeamById(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamResponse> updateTeam(@PathVariable Long id, @RequestBody TeamRequest request) {
        return ResponseEntity.ok(teamService.updateTeam(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/players")
    public ResponseEntity<TeamResponse> addPlayerToTeam(@RequestBody AddPlayerRequest request) {
        return ResponseEntity.ok(teamService.addPlayerToTeam(request));
    }

    @DeleteMapping("/{teamId}/players/{playerId}")
    public ResponseEntity<TeamResponse> removePlayerFromTeam(@PathVariable Long teamId, @PathVariable Long playerId) {
        return ResponseEntity.ok(teamService.removePlayerFromTeam(teamId, playerId));
    }

    @PatchMapping("/{id}/stats")
    public ResponseEntity<TeamResponse> updateTeamStats(@PathVariable Long id, @RequestBody UpdateStatsRequest request) {
        return ResponseEntity.ok(teamService.updateTeamStats(id, request));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<TeamResponse>> getTeamsByCategory(@PathVariable TeamCategory category) {
        return ResponseEntity.ok(teamService.getTeamsByCategory(category));
    }

    @GetMapping("/active")
    public ResponseEntity<List<TeamResponse>> getActiveTeams() {
        return ResponseEntity.ok(teamService.getActiveTeams());
    }
}