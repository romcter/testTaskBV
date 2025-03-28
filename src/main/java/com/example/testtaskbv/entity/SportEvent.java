package com.example.testtaskbv.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "sport_events")
public class SportEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(name = "home_team", nullable = false)
    private String homeTeam;

    @Column(name = "away_team", nullable = false)
    private String awayTeam;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private String sport;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String competition;

    @Column(nullable = false)
    private boolean settled;

    @OneToMany(mappedBy = "sportEvent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Market> markets = new ArrayList<>();
}