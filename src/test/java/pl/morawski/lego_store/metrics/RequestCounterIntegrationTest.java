package pl.morawski.lego_store.metrics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.morawski.lego_store.service.RequestCounterService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class RequestCounterIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RequestCounterService counterService;

    @BeforeEach
    void resetCounter() {
        counterService.reset();
    }

    @Test
    void shouldIncreaseCounterForEachRequest() throws Exception {

        long initialCount = counterService.getCount();

        mockMvc.perform(get("/api/lego-sets"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/metrics/requests"))
                .andExpect(status().isOk());

        long afterRequests = counterService.getCount();

        assertEquals(initialCount + 2, afterRequests);
    }

    @Test
    void shouldReturnCurrentRequestCount() throws Exception {

        mockMvc.perform(get("/api/lego-sets"));
        mockMvc.perform(get("/api/lego-sets"));

        mockMvc.perform(get("/api/metrics/requests"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        String.valueOf(counterService.getCount())
                ));
    }
}

