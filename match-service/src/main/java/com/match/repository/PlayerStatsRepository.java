package com.match.repository;

import com.match.entity.PlayerStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerStatsRepository extends JpaRepository<PlayerStats, Long> {
    List<PlayerStats> findByMatchId(Long matchId);
    List<PlayerStats> findByMatchIdAndTeamId(Long matchId, Long teamId);
    List<PlayerStats> findByPlayerId(Long playerId);
    List<PlayerStats> findByMatchIdAndPlayerId(Long matchId, Long playerId);
}
