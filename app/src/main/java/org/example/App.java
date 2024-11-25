package org.example;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Queue;
import java.util.LinkedList;
import java.time.LocalDateTime;

public class App {
    private static final String STOCK_SYMBOL = "^DJI"; 
    private static final Queue<String> stockQueue = new LinkedList<>();

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(App::fetchAndStoreStockData, 0, 5, TimeUnit.SECONDS);
    }

    private static void fetchAndStoreStockData() {
        try {
            Stock stock = YahooFinance.get(STOCK_SYMBOL);
            if (stock != null) {
                String stockInfo = "Price: " + stock.getQuote().getPrice() + ", Time: " + LocalDateTime.now();
                stockQueue.add(stockInfo);
                System.out.println("Stored: " + stockInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}