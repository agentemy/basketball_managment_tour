package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "player_stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayersStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long matchId;

    @Column(nullable = false)
    private Long playerId;

    @Column(nullable = false)
    private Long teamId;

    private Integer points;       // забитые очки
    private Integer fouls;        // фолы
    private Integer assists;      // ассисты
}