package com.example.service;

import com.example.dto.request.TeamRequest;
import com.example.dto.request.AddPlayerRequest;
import com.example.dto.request.UpdateStatsRequest;
import com.example.dto.response.TeamResponse;
import com.example.entity.Team;
import com.example.entity.TeamCategory;
import com.example.entity.TeamStatus;
import com.example.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamResponse createTeam(TeamRequest request) {
        Team team = Team.builder()
                .name(request.name())
                .status(request.status())
                .category(request.category())
                .wins(0)
                .losses(0)
                .build();

        Team savedTeam = teamRepository.save(team);
        return mapToResponse(savedTeam);
    }

    public List<TeamResponse> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TeamResponse getTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        return mapToResponse(team);
    }

    public TeamResponse updateTeam(Long id, TeamRequest request) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        team.setName(request.name());
        team.setStatus(request.status());
        team.setCategory(request.category());

        Team updatedTeam = teamRepository.save(team);
        return mapToResponse(updatedTeam);
    }

    public void deleteTeam(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        team.setStatus(TeamStatus.INACTIVE);
        teamRepository.save(team);
    }

    public TeamResponse addPlayerToTeam(AddPlayerRequest request) {
        Team team = teamRepository.findById(request.teamId())
                .orElseThrow(() -> new RuntimeException("Team not found"));

        if (team.getPlayerIds().contains(request.playerId())) {
            throw new RuntimeException("Player already in team");
        }

        team.getPlayerIds().add(request.playerId());

        if (Boolean.TRUE.equals(request.isCaptain())) {
            team.setCaptainId(request.playerId());
        }

        Team updatedTeam = teamRepository.save(team);
        return mapToResponse(updatedTeam);
    }

    public TeamResponse removePlayerFromTeam(Long teamId, Long playerId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        team.getPlayerIds().remove(playerId);

        if (playerId.equals(team.getCaptainId())) {
            team.setCaptainId(null);
        }

        Team updatedTeam = teamRepository.save(team);
        return mapToResponse(updatedTeam);
    }

    public TeamResponse updateTeamStats(Long id, UpdateStatsRequest request) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        if (request.wins() != null) team.setWins(request.wins());
        if (request.losses() != null) team.setLosses(request.losses());

        Team updatedTeam = teamRepository.save(team);
        return mapToResponse(updatedTeam);
    }

    public List<TeamResponse> getTeamsByCategory(TeamCategory category) {
        return teamRepository.findByStatusAndCategory(TeamStatus.ACTIVE, (category)).stream()
                .map(this::mapToResponse)
                .toList();
    }

    private TeamResponse mapToResponse(Team team) {
        return new TeamResponse(
                team.getId(),
                team.getName(),
                team.getStatus(),
                team.getCategory(),
                team.getPlayerIds(),
                team.getCaptainId(),
                team.getWins(),
                team.getLosses()
        );
    }

    public List<TeamResponse> getActiveTeams() {
        return teamRepository.findByStatus(TeamStatus.ACTIVE).stream()
                .map(this::mapToResponse)
                .toList();
    }
}