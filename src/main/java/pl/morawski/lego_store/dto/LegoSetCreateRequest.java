package pl.morawski.lego_store.dto;

import pl.morawski.lego_store.domain.ConditionType;
import pl.morawski.lego_store.domain.LegoSeries;

import java.math.BigDecimal;

public record LegoSetCreateRequest(
        String name,
        String setNumber,
        int numberOfPieces,
        int numberOfBoxes,
        int quantityInWarehouse,
        int quantityInStore,
        BigDecimal basePrice,
        LegoSeries series,
        ConditionType condition
) {}
