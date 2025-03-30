package com.example.testtaskbv;

import com.example.testtaskbv.dto.FullSportEventDTO;
import com.example.testtaskbv.entity.SportEvent;
import com.example.testtaskbv.mapper.SportEventMapper;
import com.example.testtaskbv.repository.SportEventRepository;
import com.example.testtaskbv.service.DataRetrievingService;
import com.example.testtaskbv.service.DataStoringService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataStoringServiceTest {

    @Mock
    private SportEventRepository sportEventRepository;

    @Mock
    private SportEventMapper sportEventMapper;

    @InjectMocks
    private DataStoringService dataStoringService;

    private SportEvent sportEvent;
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
    void createSportEvent_success_returnsFullSportEventDTO() {
        when(sportEventMapper.toFullSportEventDTOInverse(fullSportEventDTO)).thenReturn(sportEvent);
        when(sportEventRepository.save(sportEvent)).thenReturn(sportEvent);
        when(sportEventMapper.toFullSportEventDTO(sportEvent)).thenReturn(fullSportEventDTO);

        FullSportEventDTO result = dataStoringService.createSportEvent(fullSportEventDTO);

        assertNotNull(result);
        assertEquals(fullSportEventDTO, result);
        verify(sportEventMapper).toFullSportEventDTOInverse(fullSportEventDTO);
        verify(sportEventRepository).save(sportEvent);
        verify(sportEventMapper).toFullSportEventDTO(sportEvent);
    }

    @Test
    void updateSportEvent_found_updatesAndReturnsFullSportEventDTO() {
        Long id = 1L;
        SportEvent updatedSportEvent = new SportEvent();
        updatedSportEvent.setDescription("Updated Match");

        when(sportEventMapper.toFullSportEventDTOInverse(fullSportEventDTO)).thenReturn(updatedSportEvent);
        when(sportEventRepository.findById(id)).thenReturn(Optional.of(sportEvent));
        when(sportEventRepository.save(sportEvent)).thenReturn(sportEvent);
        when(sportEventMapper.toFullSportEventDTO(sportEvent)).thenReturn(fullSportEventDTO);

        FullSportEventDTO result = dataStoringService.updateSportEvent(id, fullSportEventDTO);

        assertNotNull(result);
        assertEquals(fullSportEventDTO, result);
        verify(sportEventMapper).toFullSportEventDTOInverse(fullSportEventDTO);
        verify(sportEventRepository).findById(id);
        verify(sportEventRepository).save(sportEvent);
        verify(sportEventMapper).toFullSportEventDTO(sportEvent);
    }

    @Test
    void updateSportEvent_notFound_throwsIllegalArgumentException() {
        Long id = 1L;
        when(sportEventMapper.toFullSportEventDTOInverse(fullSportEventDTO)).thenReturn(sportEvent);
        when(sportEventRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            dataStoringService.updateSportEvent(id, fullSportEventDTO);
        });
        assertEquals("Sport Event not found: " + id, exception.getMessage());
        verify(sportEventRepository).findById(id);
        verify(sportEventRepository, never()).save(any());
    }

    @Test
    void deleteSportEvent_found_deletesSuccessfully() {
        Long id = 1L;
        when(sportEventRepository.findById(id)).thenReturn(Optional.of(sportEvent));

        dataStoringService.deleteSportEvent(id);

        verify(sportEventRepository).findById(id);
        verify(sportEventRepository).delete(sportEvent);
    }

    @Test
    void deleteSportEvent_notFound_throwsIllegalArgumentException() {
        Long id = 1L;
        when(sportEventRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            dataStoringService.deleteSportEvent(id);
        });
        assertEquals("Sport Event not found: " + id, exception.getMessage());
        verify(sportEventRepository).findById(id);
        verify(sportEventRepository, never()).delete(any());
    }
}