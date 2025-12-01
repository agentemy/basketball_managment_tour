package com.match.controller;

import com.match.dto.request.CreateMatchRequest;
import com.match.dto.request.UpdateScoreRequest;
import com.match.dto.request.UpdatePlayerStatsRequest;
import com.match.dto.response.MatchResponse;
import com.match.dto.response.PlayerStatsResponse;
import com.match.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @PostMapping
    public ResponseEntity<MatchResponse> createMatch(@RequestBody CreateMatchRequest request) {
        return ResponseEntity.ok(matchService.createMatch(request));
    }

    @GetMapping
    public ResponseEntity<List<MatchResponse>> getAllMatches() {
        return ResponseEntity.ok(matchService.getAllMatches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchResponse> getMatchById(@PathVariable Long id) {
        return ResponseEntity.ok(matchService.getMatchById(id));
    }

    @PutMapping("/{id}/score")
    public ResponseEntity<MatchResponse> updateScore(
            @PathVariable Long id,
            @RequestBody UpdateScoreRequest request) {
        return ResponseEntity.ok(matchService.updateScore(id, request));
    }

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<MatchResponse> updateStatus(
            @PathVariable Long id,
            @PathVariable String status) {
        return ResponseEntity.ok(matchService.updateStatus(id, status));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<MatchResponse>> getMatchesByStatus(@PathVariable String status) {
        return ResponseEntity.ok(matchService.getMatchesByStatus(status));
    }

    @GetMapping("/{matchId}/stats")
    public ResponseEntity<List<PlayerStatsResponse>> getMatchStats(@PathVariable Long matchId) {
        return ResponseEntity.ok(matchService.getMatchStats(matchId));
    }

    @PostMapping("/{matchId}/stats")
    public ResponseEntity<PlayerStatsResponse> addPlayerStats(
            @PathVariable Long matchId,
            @RequestBody UpdatePlayerStatsRequest request) {
        return ResponseEntity.ok(matchService.addPlayerStats(matchId, request));
    }

    @PutMapping("/stats/{statsId}")
    public ResponseEntity<PlayerStatsResponse> updatePlayerStats(
            @PathVariable Long statsId,
            @RequestBody UpdatePlayerStatsRequest request) {
        return ResponseEntity.ok(matchService.updatePlayerStats(statsId, request));
    }

    @GetMapping("/{matchId}/stats/team/{teamId}")
    public ResponseEntity<List<PlayerStatsResponse>> getTeamStatsInMatch(
            @PathVariable Long matchId,
            @PathVariable Long teamId) {
        return ResponseEntity.ok(matchService.getTeamStatsInMatch(matchId, teamId));
    }
}