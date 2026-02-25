package pl.morawski.lego_store.dto;

import pl.morawski.lego_store.domain.AvailabilityType;
import pl.morawski.lego_store.domain.ConditionType;
import pl.morawski.lego_store.domain.LegoSeries;

import java.math.BigDecimal;

public record LegoSetResponse(
        Long id,
        String name,
        String setNumber,
        int numberOfPieces,
        int numberOfBoxes,
        int quantityInWarehouse,
        int quantityInStore,
        int quantityTotal,
        BigDecimal basePrice,
        BigDecimal finalPrice,
        LegoSeries series,
        ConditionType condition,
        AvailabilityType availabilityType,
        boolean discontinued
) {}
