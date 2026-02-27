package pl.morawski.lego_store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Lego Sets", description = "Endpoints for managing Lego sets, including filtering, sorting and stock operations.")
@RestController
@RequestMapping("/api/lego-sets")
@RequiredArgsConstructor
public class LegoSetController {

    private final LegoSetService service;

    @Operation(
            summary = "Get all Lego sets",
            description = """
                Returns a list of Lego sets.
                
                Supports:
                - Filtering by series, condition, price range, availability and discontinued flag
                - Sorting using 'sort' parameter in format: field,direction
                
                Example:
                /api/lego-sets?series=MARVEL&sort=basePrice,asc
                """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of Lego sets"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required")
    })
    @GetMapping
    public List<LegoSetResponse> getAll(
            @Parameter(description = "Filter by Lego series")
            @RequestParam(required = false) LegoSeries series,

            @Parameter(description = "Filter by condition")
            @RequestParam(required = false) ConditionType condition,

            @Parameter(description = "Minimum base price")
            @RequestParam(required = false) BigDecimal minPrice,

            @Parameter(description = "Maximum base price")
            @RequestParam(required = false) BigDecimal maxPrice,

            @Parameter(description = "Filter discontinued sets")
            @RequestParam(required = false) Boolean discontinued,

            @Parameter(description = "Filter by availability type")
            @RequestParam(required = false) AvailabilityType availabilityType,

            @Parameter(description = "Sorting in format: field,direction (e.g. basePrice,asc)")
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

    @Operation(
            summary = "Get Lego set by ID",
            description = "Returns a single Lego set by its identifier"
    )
    @GetMapping("/{id}")
    public LegoSetResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }


    @Operation(
            summary = "Create new Lego set",
            description = "Creates a new Lego set with validated input data"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Lego set created"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LegoSetResponse create(@Valid @RequestBody LegoSetCreateRequest request) {
        return service.create(request);
    }


    @Operation(
            summary = "Update Lego set",
            description = "Updates editable fields of an existing Lego set"
    )
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

    @Operation(
            summary = "Increase warehouse stock",
            description = "Increases warehouse quantity by given amount"
    )
    @PatchMapping("/{id}/warehouse/increase")
    public LegoSetResponse increaseWarehouse(
            @PathVariable Long id,
            @Valid @RequestBody StockChangeRequest request) {

        return service.increaseWarehouse(id, request.amount());
    }

    @Operation(
            summary = "Decrease warehouse stock",
            description = "Decreases warehouse quantity by given amount"
    )
    @PatchMapping("/{id}/warehouse/decrease")
    public LegoSetResponse decreaseWarehouse(
            @PathVariable Long id,
            @Valid @RequestBody StockChangeRequest request) {

        return service.decreaseWarehouse(id, request.amount());
    }

    @Operation(
            summary = "Increase store stock",
            description = "Increases store quantity by given amount"
    )
    @PatchMapping("/{id}/store/increase")
    public LegoSetResponse increaseStore(
            @PathVariable Long id,
            @Valid @RequestBody StockChangeRequest request) {

        return service.increaseStore(id, request.amount());
    }

    @Operation(
            summary = "Decrease store stock",
            description = "Decreases store quantity by given amount"
    )
    @PatchMapping("/{id}/store/decrease")
    public LegoSetResponse decreaseStore(
            @PathVariable Long id,
            @Valid @RequestBody StockChangeRequest request) {

        return service.decreaseStore(id, request.amount());
    }
}
