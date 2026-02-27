package pl.morawski.lego_store.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import pl.morawski.lego_store.domain.ConditionType;
import pl.morawski.lego_store.domain.LegoSeries;
import pl.morawski.lego_store.domain.LegoSet;
import pl.morawski.lego_store.dto.LegoSetFilter;
import pl.morawski.lego_store.specification.LegoSetSpecification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class LegoSetRepositoryTest {

    @Autowired
    private LegoSetRepository repository;

    @Test
    void shouldFindBySetNumber() {

        LegoSet legoSet = LegoSet.builder()
                .name("Millennium Falcon")
                .setNumber("75192")
                .numberOfPieces(7541)
                .numberOfBoxes(1)
                .quantityInWarehouse(10)
                .quantityInStore(5)
                .basePrice(new BigDecimal("799.99"))
                .series(LegoSeries.STAR_WARS)
                .condition(ConditionType.NEW)
                .discontinued(false)
                .build();

        repository.save(legoSet);

        Optional<LegoSet> result = repository.findBySetNumber("75192");

        assertTrue(result.isPresent());
        assertEquals("Millennium Falcon", result.get().getName());
    }

    @Test
    void shouldReturnTrueWhenSetNumberExists() {

        LegoSet legoSet = LegoSet.builder()
                .name("AT-AT")
                .setNumber("75313")
                .numberOfPieces(6785)
                .numberOfBoxes(1)
                .quantityInWarehouse(3)
                .quantityInStore(1)
                .basePrice(new BigDecimal("849.99"))
                .series(LegoSeries.STAR_WARS)
                .condition(ConditionType.NEW)
                .discontinued(false)
                .build();

        repository.save(legoSet);

        boolean exists = repository.existsBySetNumber("75313");

        assertTrue(exists);
    }

    @Test
    void shouldFilterBySeries() {

        LegoSet starWarsSet = LegoSet.builder()
                .name("X-Wing")
                .setNumber("75301")
                .numberOfPieces(474)
                .numberOfBoxes(1)
                .quantityInWarehouse(5)
                .quantityInStore(5)
                .basePrice(new BigDecimal("49.99"))
                .series(LegoSeries.STAR_WARS)
                .condition(ConditionType.NEW)
                .discontinued(false)
                .build();

        LegoSet citySet = LegoSet.builder()
                .name("City Police")
                .setNumber("60246")
                .numberOfPieces(743)
                .numberOfBoxes(1)
                .quantityInWarehouse(5)
                .quantityInStore(5)
                .basePrice(new BigDecimal("59.99"))
                .series(LegoSeries.CITY)
                .condition(ConditionType.NEW)
                .discontinued(false)
                .build();

        repository.saveAll(List.of(starWarsSet, citySet));

        LegoSetFilter filter = new LegoSetFilter(
                LegoSeries.STAR_WARS,
                null,
                null,
                null,
                null,
                null
        );

        Specification<LegoSet> spec = LegoSetSpecification.withFilters(filter);

        List<LegoSet> results = repository.findAll(spec);

        assertEquals(1, results.size());
        assertEquals(LegoSeries.STAR_WARS, results.get(0).getSeries());
    }
}
