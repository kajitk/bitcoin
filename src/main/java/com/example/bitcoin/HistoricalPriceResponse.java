package com.example.bitcoin;
import java.util.List;

public class HistoricalPriceResponse {
    private List<DailyPrice> dailyPrices;
    private double highestPrice;
    private double lowestPrice;
    private String currency;

    public HistoricalPriceResponse(List<DailyPrice> dailyPrices, double highestPrice, double lowestPrice, String currency) {
        this.dailyPrices = dailyPrices;
        this.highestPrice = highestPrice;
        this.lowestPrice = lowestPrice;
        this.currency = currency;
    }

    public List<DailyPrice> getDailyPrices() {
        return dailyPrices;
    }

    public void setDailyPrices(List<DailyPrice> dailyPrices) {
        this.dailyPrices = dailyPrices;
    }

    public double getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(double highestPrice) {
        this.highestPrice = highestPrice;
    }

    public double getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
