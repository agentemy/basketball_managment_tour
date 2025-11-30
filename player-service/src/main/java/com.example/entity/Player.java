package com.example.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "players")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "jerseyNumber", unique = true)
    private String jerseyNumber;

    @Column(name = "gender")
    private String gender;

    @Column(name = "position")
    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(name = "playerStatus")
    @Enumerated(EnumType.STRING)
    private PlayerStatus playerStatus;

    @Column(name = "team_id")
    private Long teamId;
}
