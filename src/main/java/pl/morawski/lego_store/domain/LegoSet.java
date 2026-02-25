package pl.morawski.lego_store.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lego_sets")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LegoSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String setNumber;

    @Column(nullable = false)
    private int numberOfPieces;

    @Column(nullable = false)
    private int numberOfBoxes;

    @Column(nullable = false)
    private int quantityInWarehouse;

    @Column(nullable = false)
    private int quantityInStore;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LegoSeries series;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AvailabilityType availabilityType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConditionType condition;

    @Column(nullable = false)
    private boolean discontinued;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Version
    private Long version;

    @Builder
    private LegoSet(String name,
                    String setNumber,
                    int numberOfPieces,
                    int numberOfBoxes,
                    int quantityInWarehouse,
                    int quantityInStore,
                    BigDecimal basePrice,
                    LegoSeries series,
                    ConditionType condition,
                    boolean discontinued) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }

        if (setNumber == null || setNumber.isBlank()) {
            throw new IllegalArgumentException("Set number cannot be null or blank");
        }

        if (numberOfPieces <= 0) {
            throw new IllegalArgumentException("Number of pieces must be greater than 0");
        }

        if (numberOfBoxes <= 0) {
            throw new IllegalArgumentException("Number of boxes must be greater than 0");
        }

        if (quantityInWarehouse < 0) {
            throw new IllegalArgumentException("Warehouse quantity cannot be negative");
        }

        if (quantityInStore < 0) {
            throw new IllegalArgumentException("Store quantity cannot be negative");
        }

        if (basePrice == null || basePrice.signum() < 0) {
            throw new IllegalArgumentException("Base price must be non-negative");
        }

        if (series == null) {
            throw new IllegalArgumentException("Series cannot be null");
        }

        if (condition == null) {
            throw new IllegalArgumentException("Condition cannot be null");
        }

        this.name = name;
        this.setNumber = setNumber;
        this.numberOfPieces = numberOfPieces;
        this.numberOfBoxes = numberOfBoxes;
        this.quantityInWarehouse = quantityInWarehouse;
        this.quantityInStore = quantityInStore;
        this.basePrice = basePrice;
        this.series = series;
        this.condition = condition;
        this.discontinued = discontinued;

        updateAvailability();
    }

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public void changePrice(BigDecimal newPrice) {
        if (newPrice == null || newPrice.signum() < 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.basePrice = newPrice;
    }

    public void increaseWarehouse(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.quantityInWarehouse += amount;
        updateAvailability();
    }

    public void decreaseWarehouse(int amount) {
        if (amount <= 0 || amount > this.quantityInWarehouse) {
            throw new IllegalArgumentException("Invalid stock decrease");
        }
        this.quantityInWarehouse -= amount;
        updateAvailability();
    }
    public void increaseStore(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.quantityInStore += amount;
        updateAvailability();
    }

    public void decreaseStore(int amount) {
        if (amount <= 0 || amount > this.quantityInStore) {
            throw new IllegalArgumentException("Invalid stock decrease");
        }
        this.quantityInStore -= amount;
        updateAvailability();
    }

    public void markAsDiscontinued() {
        this.discontinued = true;
    }

    private void updateAvailability() {
        this.availabilityType = AvailabilityType.from(quantityInStore, quantityInWarehouse);
    }
    public void changeCondition (ConditionType condition) {
        this.condition = condition;
    }
    public int getQuantityTotal() {
        return quantityInStore + quantityInWarehouse;
    }
    public BigDecimal getConditionDiscount() {
        return switch (condition) {
            case NEW -> BigDecimal.ZERO;
            case DAMAGED_BOX -> new BigDecimal("0.10");
            case EXHIBITION -> new BigDecimal("0.20");
        };
    }
    public BigDecimal getFinalPrice() {
        BigDecimal discount = getConditionDiscount();
        return basePrice.subtract(basePrice.multiply(discount));
    }
}
