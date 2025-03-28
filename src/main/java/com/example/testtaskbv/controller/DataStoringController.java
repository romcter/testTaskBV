package com.example.testtaskbv.controller;

import com.example.testtaskbv.dto.FullSportEventDTO;
import com.example.testtaskbv.service.DataStoringService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/store/sport-events")
public class DataStoringController {

    private final DataStoringService dataStoringService;

    @PostMapping
    public ResponseEntity<FullSportEventDTO> createSportEvent(@Valid @RequestBody FullSportEventDTO sportEventDTO) {
        return new ResponseEntity<>(dataStoringService.createSportEvent(sportEventDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FullSportEventDTO> updateSportEvent(
            @PathVariable Long id, @Valid @RequestBody FullSportEventDTO sportEventDTO) {
        return ResponseEntity.ok(dataStoringService.updateSportEvent(id, sportEventDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSportEvent(@PathVariable Long id) {
        dataStoringService.deleteSportEvent(id);
        return ResponseEntity.noContent().build();
    }
}
