package com.example.testtaskbv.mapper;

import com.example.testtaskbv.dto.FullSportEventDTO;
import com.example.testtaskbv.dto.MarketDTO;
import com.example.testtaskbv.dto.OutcomeDTO;
import com.example.testtaskbv.dto.SportEventDTO;
import com.example.testtaskbv.entity.*;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SportEventMapper {

    SportEventDTO toSportEventDTO(SportEvent sportEvent);

    FullSportEventDTO toFullSportEventDTO(SportEvent sportEvent);

    @Mapping(target = "id", ignore = true)
    void updateSportEventFromSportEvent(SportEvent source, @MappingTarget SportEvent target);

    @Mapping(source = "status", target = "status", qualifiedByName = "enumToString")
    MarketDTO toMarketDTO(Market market);

    @Mapping(source = "result", target = "result", qualifiedByName = "enumToString")
    OutcomeDTO toOutcomeDTO(Outcome outcome);

    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "markets", target = "markets", qualifiedByName = "marketDTOsToMarkets")
    SportEvent toFullSportEventDTOInverse(FullSportEventDTO sportEventDTO);

    @AfterMapping
    default void linkMarketsToSportEvent(FullSportEventDTO source, @MappingTarget SportEvent target) {
        if (target.getMarkets() != null) {
            target.getMarkets().forEach(market -> market.setSportEvent(target));
        }
    }

    @Mapping(source = "status", target = "status", qualifiedByName = "stringToMarketStatus")
    Market toMarketInverse(MarketDTO marketDTO);

    @AfterMapping
    default void linkOutcomesToMarket(MarketDTO source, @MappingTarget Market target) {
        if (target.getOutcomes() != null) {
            target.getOutcomes().forEach(outcome -> outcome.setMarket(target));
        }
    }

    @Mapping(source = "result", target = "result", qualifiedByName = "stringToResult")
    Outcome toOutcomeInverse(OutcomeDTO outcomeDTO);

    @Named("enumToString")
    default String enumToString(Enum<?> enumValue) {
        return enumValue != null ? enumValue.name() : null;
    }

    @Named("stringToMarketStatus")
    default MarketStatus stringToMarketStatus(String status) {
        return status != null ? MarketStatus.valueOf(status) : null;
    }

    @Named("stringToResult")
    default Result stringToResult(String result) {
        return result != null ? Result.valueOf(result) : null;
    }

    @Named("marketDTOsToMarkets")
    default List<Market> marketDTOsToMarkets(List<MarketDTO> marketDTOs) {
        return marketDTOs != null ? marketDTOs.stream()
                .map(this::toMarketInverse)
                .collect(Collectors.toList()) : null;
    }
}