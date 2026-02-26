package pl.morawski.lego_store.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.morawski.lego_store.domain.ConditionType;

import java.math.BigDecimal;

public record LegoSetUpdateRequest(
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotNull(message = "Base price cannot be null")
        @DecimalMin(value = "0.0", inclusive = true, message = "Base price must be non-negative")
        BigDecimal basePrice,
        @NotNull(message = "Condition cannot be null")
        ConditionType condition,
        boolean discontinued
) {}
