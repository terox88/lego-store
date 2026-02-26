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
import pl.morawski.lego_store.dto.LegoSetCreateRequest;
import pl.morawski.lego_store.exception.ResourceNotFoundException;
import pl.morawski.lego_store.service.LegoSetService;


import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import static org.mockito.Mockito.when;

@WebMvcTest(
        controllers = LegoSetController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = RequestCounterFilter.class
                )
        }
)
@AutoConfigureMockMvc(addFilters = false)
class LegoSetControllerErrorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LegoSetService service;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn404WhenLegoSetNotFound() throws Exception {

        Long id = 999L;

        when(service.getById(id))
                .thenThrow(new ResourceNotFoundException(
                        "Lego set not found with id: " + id
                ));

        mockMvc.perform(get("/api/lego-sets/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("Lego set not found with id: " + id));
    }

    @Test
    void shouldReturn400WhenCreateRequestIsInvalid() throws Exception {

        LegoSetCreateRequest invalidRequest = new LegoSetCreateRequest(
                "",
                "",
                0,
                0,
                -1,
                -1,
                BigDecimal.valueOf(-10),
                null,
                null
        );

        mockMvc.perform(post("/api/lego-sets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }
}
