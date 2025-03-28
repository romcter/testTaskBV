package com.example.testtaskbv.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SportEventDTO {
    private Long id;
    private String description;
    private String homeTeam;
    private String awayTeam;
    private LocalDateTime startTime;
    private String sport;
    private String country;
    private String competition;
    private boolean settled;
}
