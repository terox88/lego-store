package pl.morawski.lego_store.dto;

import jakarta.validation.constraints.Positive;

public record StockChangeRequest(
        @Positive(message = "Amount must be grater than 0")
        int amount
) {
}
