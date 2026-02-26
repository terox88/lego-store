package pl.morawski.lego_store.dto;

import jakarta.validation.constraints.*;
import pl.morawski.lego_store.domain.ConditionType;
import pl.morawski.lego_store.domain.LegoSeries;

import java.math.BigDecimal;

public record LegoSetCreateRequest(
        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotBlank(message = "Set number cannot be blank")
        String setNumber,

        @Positive(message = "Number of pieces must be greater than 0")
        int numberOfPieces,

        @Positive(message = "Number of boxes must be greater than 0")
        int numberOfBoxes,

        @PositiveOrZero(message = "Warehouse quantity cannot be negative")
        int quantityInWarehouse,

        @PositiveOrZero(message = "Store quantity cannot be negative")
        int quantityInStore,

        @NotNull(message = "Base price cannot be null")
        @DecimalMin(value = "0.0", inclusive = true, message = "Base price must be non-negative")
        BigDecimal basePrice,

        @NotNull(message = "Series cannot be null")
        LegoSeries series,

        @NotNull(message = "Condition cannot be null")
        ConditionType condition
) {}
