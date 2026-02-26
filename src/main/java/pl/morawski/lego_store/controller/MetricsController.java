package pl.morawski.lego_store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.morawski.lego_store.service.RequestCounterService;

@RestController
@RequestMapping("/api/metrics")
@RequiredArgsConstructor
public class MetricsController {

    private final RequestCounterService counterService;

    @GetMapping("/requests")
    public long getRequestCount() {
        return counterService.getCount();
    }
}
