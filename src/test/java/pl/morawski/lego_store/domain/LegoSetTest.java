package pl.morawski.lego_store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class LegoSetTest {

    private LegoSet createValidLegoSet(int warehouse, int store) {
        return LegoSet.builder()
                .name("Millennium Falcon")
                .setNumber("75192")
                .numberOfPieces(7541)
                .numberOfBoxes(2)
                .quantityInWarehouse(warehouse)
                .quantityInStore(store)
                .basePrice(BigDecimal.valueOf(1999.99))
                .series(LegoSeries.STAR_WARS)
                .condition(ConditionType.NEW)
                .discontinued(false)
                .build();
    }

    @Test
    @DisplayName("Should create valid LegoSet and calculate availability")
    void shouldCreateValidLegoSet() {
        LegoSet legoSet = createValidLegoSet(10, 5);

        assertEquals(10, legoSet.getQuantityInWarehouse());
        assertEquals(5, legoSet.getQuantityInStore());
        assertEquals(AvailabilityType.IN_STORE_AND_SHIPPING, legoSet.getAvailabilityType());
    }

    @Test
    @DisplayName("Should set NOT_AVAILABLE when no stock anywhere")
    void shouldSetNotAvailableWhenNoStock() {
        LegoSet legoSet = createValidLegoSet(0, 0);

        assertEquals(AvailabilityType.NOT_AVAILABLE, legoSet.getAvailabilityType());
    }

    @Test
    @DisplayName("Should increase warehouse quantity")
    void shouldIncreaseWarehouse() {
        LegoSet legoSet = createValidLegoSet(0, 0);

        legoSet.increaseWarehouse(5);

        assertEquals(5, legoSet.getQuantityInWarehouse());
        assertEquals(AvailabilityType.SHIPPING_ONLY, legoSet.getAvailabilityType());
    }

    @Test
    @DisplayName("Should decrease store quantity and update availability")
    void shouldDecreaseStore() {
        LegoSet legoSet = createValidLegoSet(0, 5);

        legoSet.decreaseStore(5);

        assertEquals(0, legoSet.getQuantityInStore());
        assertEquals(AvailabilityType.NOT_AVAILABLE, legoSet.getAvailabilityType());
    }

    @Test
    @DisplayName("Should throw exception when creating with null name")
    void shouldThrowWhenNameIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                LegoSet.builder()
                        .name(null)
                        .setNumber("123")
                        .numberOfPieces(100)
                        .numberOfBoxes(1)
                        .quantityInWarehouse(1)
                        .quantityInStore(1)
                        .basePrice(BigDecimal.TEN)
                        .series(LegoSeries.CITY)
                        .condition(ConditionType.NEW)
                        .discontinued(false)
                        .build()
        );
    }

    @Test
    @DisplayName("Should change price")
    void shouldChangePrice() {
        LegoSet legoSet = createValidLegoSet(1, 1);

        legoSet.changePrice(BigDecimal.valueOf(1500));

        assertEquals(BigDecimal.valueOf(1500), legoSet.getBasePrice());
    }
}
