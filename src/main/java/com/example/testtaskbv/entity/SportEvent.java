package com.example.testtaskbv.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "sport_events")
@EntityListeners(AuditingEntityListener.class)
@Audited
public class SportEvent extends Auditable {

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