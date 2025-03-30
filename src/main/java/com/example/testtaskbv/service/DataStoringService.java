package com.example.testtaskbv.service;

import com.example.testtaskbv.dto.FullSportEventDTO;
import com.example.testtaskbv.mapper.SportEventMapper;
import com.example.testtaskbv.repository.SportEventRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class DataStoringService {

    private final SportEventMapper sportEventMapper;
    private final SportEventRepository sportEventRepository;

    public FullSportEventDTO createSportEvent(FullSportEventDTO sportEventDTO) {
        var mappedSE = sportEventMapper.toFullSportEventDTOInverse(sportEventDTO);
        var savedEvent = sportEventRepository.save(mappedSE);
        evictNonSettledEventsCache();
        return sportEventMapper.toFullSportEventDTO(savedEvent);
    }

    public FullSportEventDTO updateSportEvent(Long id, FullSportEventDTO updatedEvent) {
        var sportEvent = sportEventMapper.toFullSportEventDTOInverse(updatedEvent);
        var existingEvent = sportEventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sport Event not found: " + id));

        sportEventMapper.updateSportEventFromSportEvent(sportEvent, existingEvent);
        var updatedEventEntity = sportEventRepository.save(existingEvent);

        evictSportEventCache(id);
        evictNonSettledEventsCache();

        return sportEventMapper.toFullSportEventDTO(updatedEventEntity);
    }

    public void deleteSportEvent(Long id) {
        var event = sportEventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sport Event not found: " + id));
        sportEventRepository.delete(event);

        evictSportEventCache(id);
        evictNonSettledEventsCache();
    }

    @CacheEvict(value = "nonSettledEvents", allEntries = true)
    public void evictNonSettledEventsCache() {
        log.info("Evicting nonSettledEvents cache");
    }

    @CacheEvict(value = "sportEvent", key = "#id")
    public void evictSportEventCache(Long id) {
        log.info("Evicting sportEvent cache for ID: {}", id);
    }
}