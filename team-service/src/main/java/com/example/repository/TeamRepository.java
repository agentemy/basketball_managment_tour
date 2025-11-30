package com.example.repository;

import com.example.entity.Team;
import com.example.entity.TeamCategory;
import com.example.entity.TeamStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    // Найти команду по названию
    Optional<Team> findByName(String name);

    // Проверить существование команды по названию
    boolean existsByName(String name);

    // Найти команды по статусу
    List<Team> findByStatus(TeamStatus status);

    // Найти команды по категории
    @Query("SELECT t FROM Team t WHERE t.category = :category")
    List<Team> findByCategory(@Param("category") String category);

    // Найти команды по статусу и категории
    List<Team> findByStatusAndCategory(TeamStatus status, TeamCategory category);

    // Найти активные команды с определенной категорией
    @Query("SELECT t FROM Team t WHERE t.status = 'ACTIVE' AND t.category = :category")
    List<Team> findActiveTeamsByCategory(@Param("category") TeamCategory category);

    // Поиск команд по названию (регистронезависимый)
    @Query("SELECT t FROM Team t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Team> findByNameContainingIgnoreCase(@Param("name") String name);

    // Найти команды с количеством побед больше указанного
    List<Team> findByWinsGreaterThan(Integer wins);

    // Найти команды с количеством поражений меньше указанного
    List<Team> findByLossesLessThan(Integer losses);

    // Найти команды по ID капитана
    @Query("SELECT t FROM Team t WHERE t.captainId = :captainId")
    List<Team> findByCaptainId(@Param("captainId") Long captainId);

    // Найти команды, в которых состоит игрок
    @Query("SELECT t FROM Team t WHERE :playerId MEMBER OF t.playerIds")
    List<Team> findTeamsByPlayerId(@Param("playerId") Long playerId);

    // Найти команды с лучшей статистикой (больше всего побед)
    @Query("SELECT t FROM Team t ORDER BY t.wins DESC, t.losses ASC")
    List<Team> findTopTeamsByWins();

    // Найти команды по диапазону побед
    @Query("SELECT t FROM Team t WHERE t.wins BETWEEN :minWins AND :maxWins")
    List<Team> findByWinsBetween(@Param("minWins") Integer minWins, @Param("maxWins") Integer maxWins);

    // Подсчитать количество команд по статусу
    @Query("SELECT COUNT(t) FROM Team t WHERE t.status = :status")
    Long countByStatus(@Param("status") TeamStatus status);

    // Подсчитать количество команд по категории
    @Query("SELECT COUNT(t) FROM Team t WHERE t.category = :category")
    Long countByCategory(@Param("category") String category);

    // Найти команды с максимальным количеством игроков
    @Query("SELECT t FROM Team t WHERE SIZE(t.playerIds) = (SELECT MAX(SIZE(t2.playerIds)) FROM Team t2)")
    List<Team> findTeamsWithMaxPlayers();

    // Найти команды с минимальным количеством игроков
    @Query("SELECT t FROM Team t WHERE SIZE(t.playerIds) = (SELECT MIN(SIZE(t2.playerIds)) FROM Team t2)")
    List<Team> findTeamsWithMinPlayers();

    // Обновить статистику команды
    @Query("UPDATE Team t SET t.wins = :wins, t.losses = :losses WHERE t.id = :teamId")
    void updateTeamStats(@Param("teamId") Long teamId, @Param("wins") Integer wins, @Param("losses") Integer losses);
}