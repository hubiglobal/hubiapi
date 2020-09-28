package org.hubi.api.contract;

import lombok.extern.slf4j.Slf4j;
import org.hubi.api.AccessKeys;
import org.hubi.api.HttpClient;
import org.hubi.api.contract.domain.*;
import org.junit.Test;

import java.util.List;
import java.util.Map;

@Slf4j
public class MarketClientTest {

    private static final String baseURL = "https://api.hubi.com";
    private static final String key = "******";
    private static final String secret = "******";
    private static final String accessToken = "******";

    private final MarketClient client = new MarketClient(
        new HttpClient(baseURL, new AccessKeys(key, secret, accessToken))
    );

    @Test
    public void refData() {
        List<RefData> result = client.refData();
        log.info("{}", result);
    }

    @Test
    public void lastPrice() {
        Map<String, String> result = client.lastPrice();
        log.info("{}", result);
    }

    @Test
    public void depth() {
        Depth result = client.depth("BTCUSD");
        log.info("{}", result);
    }

    @Test
    public void trades() {
        List<Trade> result = client.trades("BTCUSD", "");
        log.info("{}", result);
    }

    @Test
    public void klineByIndex() {
        List<Kline> result = client.klineByIndex("BTCUSD", "1H", 100, 0);
        log.info("{}", result);
    }

    @Test
    public void klineByTime() {
        List<Kline> result = client.klineByTime("BTCUSD", "1H", 100, System.currentTimeMillis() / 1000);
        log.info("{}", result);
    }

    @Test
    public void klineLatest() {
        Kline result = client.klineLatest("BTCUSD", "1H");
        log.info("{}", result);
    }

    @Test
    public void fundingRate() {
        List<FundingRate> result = client.fundingRate("BTCUSD,ETHUSD");
        log.info("{}", result);
    }

    @Test
    public void tradeStatistics() {
        List<TradeStats> result = client.tradeStatistics("BTCUSD,ETHUSD");
        log.info("{}", result);
    }

    @Test
    public void openInterest() {
        OpenInterest result = client.openInterest("BTCUSD");
        log.info("{}", result);
    }

}
