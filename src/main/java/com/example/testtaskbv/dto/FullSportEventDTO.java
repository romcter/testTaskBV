package com.example.testtaskbv.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class FullSportEventDTO {
    private Long id;
    private String description;
    private String homeTeam;
    private String awayTeam;
    private LocalDateTime startTime;
    private String sport;
    private String country;
    private String competition;
    private boolean settled;
    private List<MarketDTO> markets;
}