package com.match.service;

import com.match.dto.request.CreateMatchRequest;
import com.match.dto.request.UpdateScoreRequest;
import com.match.dto.request.UpdatePlayerStatsRequest;
import com.match.dto.response.MatchResponse;
import com.match.dto.response.PlayerStatsResponse;
import com.match.entity.Match;
import com.match.entity.MatchStatus;
import com.match.entity.PlayerStats;
import com.match.repository.MatchRepository;
import com.match.repository.PlayerStatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    private final PlayerStatsRepository playerStatsRepository;

    public MatchResponse createMatch(CreateMatchRequest request) {
        // Валидация: команды не могут играть сами с собой
        if (request.homeTeamId().equals(request.awayTeamId())) {
            throw new RuntimeException("Home team and away team cannot be the same");
        }

        Match match = Match.builder()
                .homeTeamId(request.homeTeamId())
                .awayTeamId(request.awayTeamId())
                .dateTime(request.dateTime())
                .status(MatchStatus.SCHEDULED)
                .type(request.type())
                .homeTeamScore(0)
                .awayTeamScore(0)
                .build();

        Match savedMatch = matchRepository.save(match);
        log.info("Match created with ID: {}", savedMatch.getId());

        return mapToMatchResponse(savedMatch);
    }

    public List<MatchResponse> getAllMatches() {
        return matchRepository.findAll().stream()
                .map(this::mapToMatchResponse)
                .toList();
    }

    public MatchResponse getMatchById(Long id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found"));
        return mapToMatchResponse(match);
    }

    public MatchResponse updateScore(Long id, UpdateScoreRequest request) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found"));

        // Можно менять счет только у активных матчей
        if (match.getStatus() != MatchStatus.LIVE) {
            throw new RuntimeException("Can only update score for LIVE matches");
        }

        match.setHomeTeamScore(request.homeTeamScore());
        match.setAwayTeamScore(request.awayTeamScore());

        Match updatedMatch = matchRepository.save(match);
        log.info("Score updated for match ID: {}", id);

        return mapToMatchResponse(updatedMatch);
    }

    public MatchResponse updateStatus(Long id, String status) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found"));

        MatchStatus newStatus = MatchStatus.valueOf(status.toUpperCase());
        match.setStatus(newStatus);

        Match updatedMatch = matchRepository.save(match);
        log.info("Status updated to {} for match ID: {}", newStatus, id);

        return mapToMatchResponse(updatedMatch);
    }

    public List<MatchResponse> getMatchesByStatus(String status) {
        MatchStatus matchStatus = MatchStatus.valueOf(status.toUpperCase());
        return matchRepository.findByStatus(matchStatus).stream()
                .map(this::mapToMatchResponse)
                .toList();
    }

    public List<PlayerStatsResponse> getMatchStats(Long matchId) {
        return playerStatsRepository.findByMatchId(matchId).stream()
                .map(this::mapToPlayerStatsResponse)
                .toList();
    }

    public PlayerStatsResponse addPlayerStats(Long matchId, UpdatePlayerStatsRequest request) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found"));

        if (!request.teamId().equals(match.getHomeTeamId()) &&
                !request.teamId().equals(match.getAwayTeamId())) {
            throw new RuntimeException("Team is not participating in this match");
        }

        boolean statsExists = playerStatsRepository.findByMatchIdAndPlayerId(matchId, request.playerId())
                .stream()
                .findFirst()
                .isPresent();

        if (statsExists) {
            throw new RuntimeException("Stats for this player already exist in this match");
        }

        PlayerStats stats = PlayerStats.builder()
                .matchId(matchId)
                .playerId(request.playerId())
                .teamId(request.teamId())
                .points(request.points() != null ? request.points() : 0)
                .fouls(request.fouls() != null ? request.fouls() : 0)
                .assists(request.assists() != null ? request.assists() : 0)
                .build();

        PlayerStats savedStats = playerStatsRepository.save(stats);
        log.info("Player stats added for player ID: {} in match ID: {}", request.playerId(), matchId);

        return mapToPlayerStatsResponse(savedStats);
    }

    public PlayerStatsResponse updatePlayerStats(Long statsId, UpdatePlayerStatsRequest request) {
        PlayerStats stats = playerStatsRepository.findById(statsId)
                .orElseThrow(() -> new RuntimeException("Player stats not found"));

        if (request.points() != null) stats.setPoints(request.points());
        if (request.fouls() != null) stats.setFouls(request.fouls());
        if (request.assists() != null) stats.setAssists(request.assists());

        PlayerStats updatedStats = playerStatsRepository.save(stats);
        return mapToPlayerStatsResponse(updatedStats);
    }

    public List<PlayerStatsResponse> getTeamStatsInMatch(Long matchId, Long teamId) {
        return playerStatsRepository.findByMatchIdAndTeamId(matchId, teamId).stream()
                .map(this::mapToPlayerStatsResponse)
                .toList();
    }

    private MatchResponse mapToMatchResponse(Match match) {
        return new MatchResponse(
                match.getId(),
                match.getHomeTeamId(),
                match.getAwayTeamId(),
                match.getDateTime(),
                match.getStatus(),
                match.getType(),
                match.getHomeTeamScore(),
                match.getAwayTeamScore()
        );
    }

    private PlayerStatsResponse mapToPlayerStatsResponse(PlayerStats stats) {
        return new PlayerStatsResponse(
                stats.getPlayerId(),
                stats.getPoints(),
                stats.getFouls(),
                stats.getAssists()
        );
    }
}