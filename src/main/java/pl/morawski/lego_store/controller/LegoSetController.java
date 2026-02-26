package pl.morawski.lego_store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.morawski.lego_store.domain.AvailabilityType;
import pl.morawski.lego_store.domain.ConditionType;
import pl.morawski.lego_store.domain.LegoSeries;
import pl.morawski.lego_store.dto.*;
import pl.morawski.lego_store.service.LegoSetService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/lego-sets")
@RequiredArgsConstructor
public class LegoSetController {

    private final LegoSetService service;

    @GetMapping
    public List<LegoSetResponse> getAll(
            @RequestParam(required = false) LegoSeries series,
            @RequestParam(required = false) ConditionType condition,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Boolean discontinued,
            @RequestParam(required = false) AvailabilityType availabilityType,
            @RequestParam(defaultValue = "id,asc") String sort
    ) {
        LegoSetFilter filter = new LegoSetFilter(
                series,
                condition,
                minPrice,
                maxPrice,
                discontinued,
                availabilityType
        );

        Sort sortOrder = parseSort(sort);

        return service.getAll(filter, sortOrder);
    }

    @GetMapping("/{id}")
    public LegoSetResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LegoSetResponse create(@Valid @RequestBody LegoSetCreateRequest request) {
        return service.create(request);
    }


    @PutMapping("/{id}")
    public LegoSetResponse update(
            @PathVariable Long id,
            @Valid @RequestBody LegoSetUpdateRequest request
    ) {
        return service.update(id, request);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }


    private Sort parseSort(String sort) {
        String[] parts = sort.split(",");

        if (parts.length != 2) {
            throw new IllegalArgumentException(
                    "Sort parameter must be in format: field,direction"
            );
        }

        Sort.Direction direction = Sort.Direction.fromString(parts[1]);
        return Sort.by(direction, parts[0]);
    }

    @PatchMapping("/{id}/warehouse/increase")
    public LegoSetResponse increaseWarehouse(
            @PathVariable Long id,
            @Valid @RequestBody StockChangeRequest request) {

        return service.increaseWarehouse(id, request.amount());
    }

    @PatchMapping("/{id}/warehouse/decrease")
    public LegoSetResponse decreaseWarehouse(
            @PathVariable Long id,
            @Valid @RequestBody StockChangeRequest request) {

        return service.decreaseWarehouse(id, request.amount());
    }

    @PatchMapping("/{id}/store/increase")
    public LegoSetResponse increaseStore(
            @PathVariable Long id,
            @Valid @RequestBody StockChangeRequest request) {

        return service.increaseStore(id, request.amount());
    }

    @PatchMapping("/{id}/store/decrease")
    public LegoSetResponse decreaseStore(
            @PathVariable Long id,
            @Valid @RequestBody StockChangeRequest request) {

        return service.decreaseStore(id, request.amount());
    }
}
