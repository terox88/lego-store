package pl.morawski.lego_store.dto;

import pl.morawski.lego_store.domain.AvailabilityType;
import pl.morawski.lego_store.domain.ConditionType;
import pl.morawski.lego_store.domain.LegoSeries;

import java.math.BigDecimal;

public record LegoSetFilter(
        LegoSeries series,
        ConditionType condition,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Boolean discontinued,
        AvailabilityType availabilityType
) {}
