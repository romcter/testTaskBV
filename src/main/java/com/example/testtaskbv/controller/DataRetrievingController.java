package com.example.testtaskbv.controller;

import com.example.testtaskbv.dto.FullSportEventDTO;
import com.example.testtaskbv.dto.SportEventDTO;
import com.example.testtaskbv.service.DataRetrievingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/retrieve/sport-events")
public class DataRetrievingController {

    private final DataRetrievingService dataRetrievingService;

    @GetMapping
    public ResponseEntity<List<SportEventDTO>> getNonSettledSportEvents(
            @RequestParam(required = false) String sport) {
        List<SportEventDTO> events = dataRetrievingService.getNonSettledSportEvents(sport);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullSportEventDTO> getSportEventById(@PathVariable Long id) {
        FullSportEventDTO event = dataRetrievingService.getSportEventById(id);
        return ResponseEntity.ok(event);
    }
}