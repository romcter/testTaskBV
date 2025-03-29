package com.example.testtaskbv.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "markets")
@Data
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MarketStatus status;

    @Column(nullable = false)
    private boolean settled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sport_event_id", nullable = false)
    private SportEvent sportEvent;

    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Outcome> outcomes = new ArrayList<>();
}
