package com.example.testtaskbv.dto;

import lombok.Data;

import java.util.List;

@Data
public class MarketDTO {
    private Long id;
    private String description;
    private String status;
    private boolean settled;
    private List<OutcomeDTO> outcomes;
}
