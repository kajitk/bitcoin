package com.example.bitcoin;

        import com.fasterxml.jackson.databind.ObjectMapper;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
        import org.springframework.boot.test.mock.mockito.MockBean;
        import org.springframework.test.web.servlet.MockMvc;

        import java.util.Arrays;

        import static org.mockito.ArgumentMatchers.anyBoolean;
        import static org.mockito.ArgumentMatchers.anyString;
        import static org.mockito.Mockito.when;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BitcoinPriceController.class)
public class BitcoinPriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BitcoinPriceController bitcoinPriceController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        when(bitcoinPriceController.getHistoricalPrices(anyString(), anyString(), anyString()))
                .thenReturn(new HistoricalPriceResponse(
                        Arrays.asList(
                                new DailyPrice("01-01-2023", 48000.0),
                                new DailyPrice("02-01-2023", 47000.0)
                        ),
                        48000.0,
                        47000.0,
                        47000.0,
                        "USD"
                ));

       // when(bitcoinPriceController.toggleOfflineMode(anyBoolean())).thenReturn(null);
    }

    @Test
    public void testGetHistoricalPrices() throws Exception {
        mockMvc.perform(get("/api/bitcoin/historical-prices")
                .param("startDate", "01-01-2023")
                .param("endDate", "02-01-2023")
                .param("outputCurrency", "USD"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        new HistoricalPriceResponse(
                                Arrays.asList(
                                        new DailyPrice("01-01-2023", 48000.0),
                                        new DailyPrice("02-01-2023", 47000.0)
                                ),
                                48000.0,
                                47000.0,
                                47000.0,
                                "USD"
                        )
                )));
    }

    @Test
    public void testToggleOfflineMode() throws Exception {
        mockMvc.perform(post("/api/bitcoin/toggle-offline")
                .param("offline", "true"))
                .andExpect(status().isOk());
    }
}
