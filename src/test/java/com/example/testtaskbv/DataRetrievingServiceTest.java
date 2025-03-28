package com.example.testtaskbv;

import com.example.testtaskbv.dto.FullSportEventDTO;
import com.example.testtaskbv.dto.SportEventDTO;
import com.example.testtaskbv.entity.SportEvent;
import com.example.testtaskbv.mapper.SportEventMapper;
import com.example.testtaskbv.repository.SportEventRepository;
import com.example.testtaskbv.service.DataRetrievingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataRetrievingServiceTest {

    @Mock
    private SportEventRepository sportEventRepository;

    @Mock
    private SportEventMapper sportEventMapper;

    @InjectMocks
    private DataRetrievingService dataRetrievingService;

    private SportEvent sportEvent;
    private SportEventDTO sportEventDTO;
    private FullSportEventDTO fullSportEventDTO;

    @BeforeEach
    void setUp() {
        sportEvent = new SportEvent();
        sportEvent.setId(1L);
        sportEvent.setDescription("Arsenal v Manchester United");
        sportEvent.setHomeTeam("Arsenal");
        sportEvent.setAwayTeam("Manchester United");
        sportEvent.setStartTime(LocalDateTime.of(2025, 1, 31, 18, 0));
        sportEvent.setSport("Football");
        sportEvent.setCountry("England");
        sportEvent.setCompetition("Premier League");
        sportEvent.setSettled(false);

        sportEventDTO = new SportEventDTO();
        sportEventDTO.setId(1L);
        sportEventDTO.setDescription("Arsenal v Manchester United");
        sportEventDTO.setHomeTeam("Arsenal");
        sportEventDTO.setAwayTeam("Manchester United");
        sportEventDTO.setStartTime(LocalDateTime.of(2025, 1, 31, 18, 0));
        sportEventDTO.setSport("Football");
        sportEventDTO.setCountry("England");
        sportEventDTO.setCompetition("Premier League");
        sportEventDTO.setSettled(false);

        fullSportEventDTO = new FullSportEventDTO();
        fullSportEventDTO.setId(1L);
        fullSportEventDTO.setDescription("Arsenal v Manchester United");
        fullSportEventDTO.setHomeTeam("Arsenal");
        fullSportEventDTO.setAwayTeam("Manchester United");
        fullSportEventDTO.setStartTime(LocalDateTime.of(2025, 1, 31, 18, 0));
        fullSportEventDTO.setSport("Football");
        fullSportEventDTO.setCountry("England");
        fullSportEventDTO.setCompetition("Premier League");
        fullSportEventDTO.setSettled(false);
    }

    @Test
    void getNonSettledSportEvents_withSport_returnsFilteredEvents() {
        String sport = "Football";
        List<SportEvent> events = List.of(sportEvent);
        when(sportEventRepository.findBySettledFalseAndSportOrderByStartTime(sport)).thenReturn(events);
        when(sportEventMapper.toSportEventDTO(sportEvent)).thenReturn(sportEventDTO);

        List<SportEventDTO> result = dataRetrievingService.getNonSettledSportEvents(sport);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sportEventDTO, result.get(0));
        verify(sportEventRepository).findBySettledFalseAndSportOrderByStartTime(sport);
        verify(sportEventMapper).toSportEventDTO(sportEvent);
    }

    @Test
    void getNonSettledSportEvents_withoutSport_returnsAllNonSettledEvents() {
        List<SportEvent> events = List.of(sportEvent);
        when(sportEventRepository.findBySettledFalseOrderByStartTime()).thenReturn(events);
        when(sportEventMapper.toSportEventDTO(sportEvent)).thenReturn(sportEventDTO);

        List<SportEventDTO> result = dataRetrievingService.getNonSettledSportEvents(null);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sportEventDTO, result.get(0));
        verify(sportEventRepository).findBySettledFalseOrderByStartTime();
        verify(sportEventMapper).toSportEventDTO(sportEvent);
    }

    @Test
    void getNonSettledSportEvents_emptyResult_returnsEmptyList() {
        when(sportEventRepository.findBySettledFalseOrderByStartTime()).thenReturn(Collections.emptyList());

        List<SportEventDTO> result = dataRetrievingService.getNonSettledSportEvents(null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(sportEventRepository).findBySettledFalseOrderByStartTime();
        verify(sportEventMapper, never()).toSportEventDTO(any());
    }

    @Test
    void getSportEventById_found_returnsFullSportEventDTO() {
        Long id = 1L;
        when(sportEventRepository.findById(id)).thenReturn(Optional.of(sportEvent));
        when(sportEventMapper.toFullSportEventDTO(sportEvent)).thenReturn(fullSportEventDTO);

        FullSportEventDTO result = dataRetrievingService.getSportEventById(id);

        assertNotNull(result);
        assertEquals(fullSportEventDTO, result);
        verify(sportEventRepository).findById(id);
        verify(sportEventMapper).toFullSportEventDTO(sportEvent);
    }

    @Test
    void getSportEventById_notFound_throwsIllegalArgumentException() {
        Long id = 1L;
        when(sportEventRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            dataRetrievingService.getSportEventById(id);
        });
        assertEquals("Sport Event not found: " + id, exception.getMessage());
        verify(sportEventRepository).findById(id);
        verify(sportEventMapper, never()).toFullSportEventDTO(any());
    }
}