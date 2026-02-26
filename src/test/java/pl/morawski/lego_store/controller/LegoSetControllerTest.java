package pl.morawski.lego_store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.morawski.lego_store.configuration.RequestCounterFilter;
import pl.morawski.lego_store.domain.AvailabilityType;
import pl.morawski.lego_store.domain.ConditionType;
import pl.morawski.lego_store.domain.LegoSeries;
import pl.morawski.lego_store.dto.LegoSetCreateRequest;
import pl.morawski.lego_store.dto.LegoSetResponse;
import pl.morawski.lego_store.exception.GlobalExceptionHandler;
import pl.morawski.lego_store.service.LegoSetService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(
        controllers = LegoSetController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = {
                                RequestCounterFilter.class,
                                GlobalExceptionHandler.class
                        }
                )
        }
)
class LegoSetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LegoSetService service;

    @Test
    void shouldReturnListOfLegoSets() throws Exception {

        LegoSetResponse response = new LegoSetResponse(
                1L,
                "Millennium Falcon",
                "75192",
                7541,
                2,
                10,
                5,
                15,
                new BigDecimal("1999.99"),
                new BigDecimal("1999.99"),
                LegoSeries.STAR_WARS,
                ConditionType.NEW,
                AvailabilityType.IN_STORE_AND_SHIPPING,
                false
        );

        when(service.getAll(any(), any()))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/lego-sets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Millennium Falcon"))
                .andExpect(jsonPath("$[0].setNumber").value("75192"))
                .andExpect(jsonPath("$[0].quantityTotal").value(15));
    }

    @Test
    void shouldReturnLegoSetById() throws Exception {

        Long id = 1L;

        LegoSetResponse response = new LegoSetResponse(
                id,
                "Millennium Falcon",
                "75192",
                7541,
                2,
                10,
                5,
                15,
                new BigDecimal("1999.99"),
                new BigDecimal("1999.99"),
                LegoSeries.STAR_WARS,
                ConditionType.NEW,
                AvailabilityType.IN_STORE_AND_SHIPPING,
                false
        );

        when(service.getById(id)).thenReturn(response);

        mockMvc.perform(get("/api/lego-sets/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Millennium Falcon"))
                .andExpect(jsonPath("$.setNumber").value("75192"))
                .andExpect(jsonPath("$.quantityTotal").value(15));
    }

    @Test
    void shouldCreateLegoSet() throws Exception {

        LegoSetCreateRequest request = new LegoSetCreateRequest(
                "Millennium Falcon",
                "75192",
                7541,
                2,
                10,
                5,
                new BigDecimal("1999.99"),
                LegoSeries.STAR_WARS,
                ConditionType.NEW
        );

        LegoSetResponse response = new LegoSetResponse(
                1L,
                request.name(),
                request.setNumber(),
                request.numberOfPieces(),
                request.numberOfBoxes(),
                request.quantityInWarehouse(),
                request.quantityInStore(),
                15,
                request.basePrice(),
                request.basePrice(),
                request.series(),
                request.condition(),
                AvailabilityType.IN_STORE_AND_SHIPPING,
                false
        );

        when(service.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/lego-sets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Millennium Falcon"))
                .andExpect(jsonPath("$.setNumber").value("75192"));
    }

}
