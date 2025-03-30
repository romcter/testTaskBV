package com.example.testtaskbv.service;

import com.example.testtaskbv.dto.FullSportEventDTO;
import com.example.testtaskbv.dto.SportEventDTO;
import com.example.testtaskbv.mapper.SportEventMapper;
import com.example.testtaskbv.repository.SportEventRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class DataRetrievingService {

    private final SportEventRepository sportEventRepository;
    private final SportEventMapper sportEventMapper;

    @Cacheable(value = "nonSettledEvents", key = "#sport != null ? #sport : 'all'", unless = "#result.isEmpty()")
    public List<SportEventDTO> getNonSettledSportEvents(String sport) {
        log.info("Fetching non-settled events for sport: {}", sport);
        var events = (sport != null && !sport.isEmpty())
                ? sportEventRepository.findBySettledFalseAndSportOrderByStartTime(sport)
                : sportEventRepository.findBySettledFalseOrderByStartTime();
        return events.stream()
                .map(sportEventMapper::toSportEventDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "sportEvent", key = "#id")
    public FullSportEventDTO getSportEventById(Long id) {
        log.info("Fetching sport event by ID: {}", id);
        var event = sportEventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sport Event not found: " + id));
        return sportEventMapper.toFullSportEventDTO(event);
    }
}