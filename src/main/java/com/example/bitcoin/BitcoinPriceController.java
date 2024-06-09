package com.example.bitcoin;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/bitcoin")
public class BitcoinPriceController {

    private boolean offlineMode = false;

    @GetMapping("/historical-prices")
    public HistoricalPriceResponse getHistoricalPrices(
            @Parameter(description = "Start date in DD-MM-YYYY format")
            @RequestParam String startDate,
            @Parameter(description = "End date in DD-MM-YYYY format")
            @RequestParam String endDate,
            @Parameter(description = "Output currency (e.g., USD, EUR)")
            @RequestParam String outputCurrency) {

        try {
            List<DailyPrice> dailyPrices = offlineMode ? fetchOfflinePrices() : fetchHistoricalPrices(startDate, endDate);
            double highestPrice = findHighestPrice(dailyPrices);
            double lowestPrice = findLowestPrice(dailyPrices);
            double currentPrice = fetchCurrentPrice(outputCurrency);

            return new HistoricalPriceResponse(dailyPrices, highestPrice, lowestPrice, currentPrice, outputCurrency);

        } catch (Exception e) {
            throw new RuntimeException("Error fetching historical prices", e);
        }
    }

    private List<DailyPrice> fetchHistoricalPrices(String startDate, String endDate) throws ParseException {
       // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputSdf = new SimpleDateFormat("dd-MM-yyyy");
        List<DailyPrice> prices = new ArrayList<>();
        Date start = sdf.parse(startDate);
        Date end = sdf.parse(endDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);

        while (!calendar.getTime().after(end)) {
            String formattedDate = outputSdf.format(calendar.getTime());
            prices.add(new DailyPrice(formattedDate, 50000.0 + (Math.random() * 10000 - 5000))); // Random prices for demo
            calendar.add(Calendar.DATE, 1);
        }
        return prices;
    }

    private List<DailyPrice> fetchOfflinePrices() {
        // Dummy data for offline mode
        List<DailyPrice> prices = new ArrayList<>();
        prices.add(new DailyPrice("01-01-2023", 48000.0));
        prices.add(new DailyPrice("02-01-2023", 47000.0));
        prices.add(new DailyPrice("06-01-2023", 46000.0));
        prices.add(new DailyPrice("05-01-2023", 43000.0));
        prices.add(new DailyPrice("03-01-2023", 41000.0));
        prices.add(new DailyPrice("04-01-2023", 45000.0));
        return prices;
    }

    private double findHighestPrice(List<DailyPrice> prices) {
        return prices.stream().mapToDouble(DailyPrice::getPrice).max().orElse(0.0);
    }

    private double findLowestPrice(List<DailyPrice> prices) {
        return prices.stream().mapToDouble(DailyPrice::getPrice).min().orElse(0.0);
    }

    private double fetchCurrentPrice(String currency) {
        return 47000.0;
    }

    @PostMapping("/toggle-offline")
    public void toggleOfflineMode(@RequestParam boolean offline) {
        this.offlineMode = offline;
    }
}
