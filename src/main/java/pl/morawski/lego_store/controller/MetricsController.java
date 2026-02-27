package pl.morawski.lego_store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.morawski.lego_store.service.RequestCounterService;

@Tag(name = "Metrics", description = "Application metrics endpoints")
@RestController
@RequestMapping("/api/metrics")
@RequiredArgsConstructor
public class MetricsController {


    private final RequestCounterService counterService;

    @Operation(
            summary = "Get request count",
            description = "Returns total number of HTTP requests handled by the application"
    )
    @GetMapping("/requests")
    public long getRequestCount() {
        return counterService.getCount();
    }
}
