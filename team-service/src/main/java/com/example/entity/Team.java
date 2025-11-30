package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeamStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeamCategory category;

    @ElementCollection
    @CollectionTable(name = "team_players", joinColumns = @JoinColumn(name = "team_id"))
    @Column(name = "player_id")
    private List<Long> playerIds = new ArrayList<>();

    @Column(name = "captain_id")
    private Long captainId;

    @Builder.Default
    private Integer wins = 0;

    @Builder.Default
    private Integer losses = 0;
}