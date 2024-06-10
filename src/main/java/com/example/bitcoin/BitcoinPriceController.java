package com.example.bitcoin;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bitcoin")
public class BitcoinPriceController {
    private static final Logger logger = LoggerFactory.getLogger(BitcoinPriceController.class);

    private boolean offlineMode = false;

    @GetMapping("/historical-prices")
    public HistoricalPriceResponse getHistoricalPrices(
            @Parameter(description = "Start date in DD-MM-YYYY format")
            @RequestParam String startDate,
            @Parameter(description = "End date in DD-MM-YYYY format")
            @RequestParam String endDate,
            @Parameter(description = "Output currency (e.g., USD, EUR)")
            @RequestParam String outputCurrency) {

        HistoricalPriceResponse response;
        logger.info("Received request for historical prices from {} to {} with output currency {}", startDate, endDate, outputCurrency);

        try {
            List<DailyPrice> dailyPrices = offlineMode ? fetchOfflinePrices(outputCurrency) : fetchHistoricalPrices(startDate, endDate, outputCurrency);
            double highestPrice = findHighestPrice(dailyPrices);
            double lowestPrice = findLowestPrice(dailyPrices);
            logger.info("Highest price: {}, Lowest price: {}", highestPrice, lowestPrice);

           // return new HistoricalPriceResponse(dailyPrices, highestPrice, lowestPrice, outputCurrency);
            response = new HistoricalPriceResponse(dailyPrices, highestPrice, lowestPrice, outputCurrency);
            logger.info("Returning response: {}", response);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching historical prices", e);
        }
        return response;

    }

    private List<DailyPrice> fetchHistoricalPrices(String startDate, String endDate, String currency) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        List<DailyPrice> prices = new ArrayList<>();
        Date start = sdf.parse(startDate);
        Date end = sdf.parse(endDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);

        while (!calendar.getTime().after(end)) {
            String formattedDate = sdf.format(calendar.getTime());
            double price =50000.0 + (Math.random() * 10000 - 5000);
            //convertedPrice=fetchCurrencyPrice(currency,price);
            prices.add(new DailyPrice(formattedDate, fetchCurrencyPrice(currency,price))); // Random prices
            calendar.add(Calendar.DATE, 1);
        }
        return prices;
    }

    private List<DailyPrice> fetchOfflinePrices(String currency) {
        // Dummy data for offline mode
        List<DailyPrice> prices = new ArrayList<>();
        prices.add(new DailyPrice("01-01-2023", fetchCurrencyPrice(currency,48000.0)));
        prices.add(new DailyPrice("02-01-2023", fetchCurrencyPrice(currency,47000.0)));
        prices.add(new DailyPrice("06-01-2023", fetchCurrencyPrice(currency,46000.0)));
        prices.add(new DailyPrice("05-01-2023", fetchCurrencyPrice(currency,43000.0)));
        prices.add(new DailyPrice("03-01-2023", fetchCurrencyPrice(currency,41000.0)));
        prices.add(new DailyPrice("04-01-2023", fetchCurrencyPrice(currency,45000.0)));
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
    private double fetchCurrencyPrice(String currency,double price) {
        double convertedPrice;
        Map<String,Double> rates= new HashMap<>();

        rates.put("INR",82.743);
        rates.put("EUR",0.943);
        rates.put("CAD",1.351);
        rates.put("JPY",132.440);
        rates.put("CNY",6.871);

        if(rates.containsKey(currency)){
            convertedPrice =price* rates.get(currency);
        }else{
            convertedPrice=price;//default to USD
        }
        return convertedPrice;
    }

    @PostMapping("/toggle-offline")
    public void toggleOfflineMode(@RequestParam boolean offline) {
        this.offlineMode = offline;
        logger.info("Offline mode set to {}", offline);

    }
}
