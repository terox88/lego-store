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
    private Integer numberOfPieces;

    @Column(nullable = false)
    private Integer numberOfBoxes;

    @Column(nullable = false)
    private Integer quantityInStock;

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
    private Boolean discontinued;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Version
    private Long version;

    @Builder
    private LegoSet(String name,
                    String setNumber,
                    Integer numberOfPieces,
                    Integer numberOfBoxes,
                    Integer quantityInStock,
                    BigDecimal basePrice,
                    LegoSeries series,
                    AvailabilityType availabilityType,
                    ConditionType condition,
                    Boolean discontinued) {

        this.name = name;
        this.setNumber = setNumber;
        this.numberOfPieces = numberOfPieces;
        this.numberOfBoxes = numberOfBoxes;
        this.quantityInStock = quantityInStock;
        this.basePrice = basePrice;
        this.series = series;
        this.availabilityType = availabilityType;
        this.condition = condition;
        this.discontinued = discontinued;
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

    public void increaseStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.quantityInStock += amount;
    }

    public void decreaseStock(int amount) {
        if (amount <= 0 || amount > this.quantityInStock) {
            throw new IllegalArgumentException("Invalid stock decrease");
        }
        this.quantityInStock -= amount;
    }

    public void markAsDiscontinued() {
        this.discontinued = true;
    }
}
