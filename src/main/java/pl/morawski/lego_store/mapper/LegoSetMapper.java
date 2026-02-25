package pl.morawski.lego_store.mapper;

import org.springframework.stereotype.Component;
import pl.morawski.lego_store.domain.LegoSet;
import pl.morawski.lego_store.dto.LegoSetCreateRequest;
import pl.morawski.lego_store.dto.LegoSetResponse;

@Component
public class LegoSetMapper {

    public LegoSet toEntity(LegoSetCreateRequest request) {
        return LegoSet.builder()
                .name(request.name())
                .setNumber(request.setNumber())
                .numberOfPieces(request.numberOfPieces())
                .numberOfBoxes(request.numberOfBoxes())
                .quantityInWarehouse(request.quantityInWarehouse())
                .quantityInStore(request.quantityInStore())
                .basePrice(request.basePrice())
                .series(request.series())
                .condition(request.condition())
                .discontinued(false)
                .build();
    }

    public LegoSetResponse toResponse(LegoSet entity) {
        return new LegoSetResponse(
                entity.getId(),
                entity.getName(),
                entity.getSetNumber(),
                entity.getNumberOfPieces(),
                entity.getNumberOfBoxes(),
                entity.getQuantityInWarehouse(),
                entity.getQuantityInStore(),
                entity.getQuantityTotal(),
                entity.getBasePrice(),
                entity.getFinalPrice(),
                entity.getSeries(),
                entity.getCondition(),
                entity.getAvailabilityType(),
                entity.isDiscontinued()
        );
    }
}
