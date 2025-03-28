package com.example.testtaskbv.dto;

import lombok.Data;

@Data
public class OutcomeDTO {
    private Long id;
    private String description;
    private boolean settled;
    private double price;
    private String result;
}