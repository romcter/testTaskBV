package com.example.testtaskbv.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "outcomes")
@Data
public class Outcome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean settled;

    @Column(nullable = false)
    private double price;

    @Enumerated(EnumType.STRING)
    @Column
    private Result result;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id", nullable = false)
    private Market market;
}

