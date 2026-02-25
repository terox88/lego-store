package pl.morawski.lego_store.dto;

import pl.morawski.lego_store.domain.ConditionType;

import java.math.BigDecimal;

public record LegoSetUpdateRequest(
        String name,
        BigDecimal basePrice,
        ConditionType condition,
        boolean discontinued
) {}
