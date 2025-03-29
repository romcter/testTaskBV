package com.example.testtaskbv.service;

import com.example.testtaskbv.dto.FullSportEventDTO;
import com.example.testtaskbv.mapper.SportEventMapper;
import com.example.testtaskbv.repository.SportEventRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class DataStoringService {

    private final SportEventMapper sportEventMapper;
    private final SportEventRepository sportEventRepository;
    private final DataRetrievingService dataRetrievingService;

//    @CacheEvict(value = "nonSettledEvents", allEntries = true)
    public FullSportEventDTO createSportEvent(FullSportEventDTO sportEventDTO) {
        var mappedSE = sportEventMapper.toFullSportEventDTOInverse(sportEventDTO);
        var savedEvent = sportEventRepository.save(mappedSE);
        dataRetrievingService.evictNonSettledEventsCache();
        return sportEventMapper.toFullSportEventDTO(savedEvent);
    }

//    @CacheEvict(value = {"nonSettledEvents", "sportEvent"}, allEntries = true, key = "#id")
    public FullSportEventDTO updateSportEvent(Long id, FullSportEventDTO updatedEvent) {
        var sportEvent = sportEventMapper.toFullSportEventDTOInverse(updatedEvent);
        var existingEvent = sportEventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sport Event not found: " + id));

        sportEventMapper.updateSportEventFromSportEvent(sportEvent, existingEvent);
        var updatedEventEntity = sportEventRepository.save(existingEvent);

        dataRetrievingService.evictSportEventCache(id);
        dataRetrievingService.evictNonSettledEventsCache();

        return sportEventMapper.toFullSportEventDTO(updatedEventEntity);
    }

//    @CacheEvict(value = {"nonSettledEvents", "sportEvent"}, allEntries = true, key = "#id")
    public void deleteSportEvent(Long id) {
        var event = sportEventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sport Event not found: " + id));
        sportEventRepository.delete(event);

        dataRetrievingService.evictSportEventCache(id);
        dataRetrievingService.evictNonSettledEventsCache();
    }
}