package org.hubi.api.contract;

import lombok.extern.slf4j.Slf4j;
import org.hubi.api.AccessKeys;
import org.hubi.api.HttpClient;
import org.hubi.api.contract.domain.AssetAccount;
import org.hubi.api.contract.domain.Order;
import org.hubi.api.contract.domain.Position;
import org.hubi.api.contract.domain.StringResult;
import org.junit.Test;

import java.util.List;

@Slf4j
public class TradeClientTest {

    private static final String baseURL = "https://api.hubi.com";
    private static final String key = "******";
    private static final String secret = "******";
    private static final String accessToken = "******";

    private final TradeClient client = new TradeClient(
        new HttpClient(baseURL, new AccessKeys(key, secret, accessToken))
    );

    @Test
    public void queryOrdersPro() {
        List<Order> result = client.queryOrdersPro();
        log.info("{}", result);
    }

    @Test
    public void queryOrderById() {
        Order result = client.queryOrderById("");
        log.info("{}", result);
    }

    @Test
    public void queryActiveOrders() {
        List<Order> result = client.queryActiveOrders("BTCUSD");
        log.info("{}", result);
    }

    @Test
    public void queryAccounts() {
        List<AssetAccount> result = client.queryAccounts();
        log.info("{}", result);
    }

    @Test
    public void queryPosition() {
        List<Position> result = client.queryPosition();
        log.info("{}", result);
    }

    @Test
    public void enterOrder() {
        StringResult result = client.enterMarketOrder(
            "USDT",
            "BTCUSD", true,
            TradeClient.TradeDirection.BUY,
            100
        );
        log.info("{}", result);
    }

    @Test
    public void cancelOrder() {
        StringResult result = client.cancelOrder("dddd");
        log.info("{}", result);
    }

    @Test
    public void closePosition() {
        StringResult result = client.closePosition("USDT", "BTCUSD", TradeClient.PositionType.LONG);
        log.info("{}", result);
    }

    @Test
    public void switchToCross() {
        StringResult result = client.switchToCross("USDT");
        log.info("{}", result);
    }

    @Test
    public void changePositionLeverage() {
        StringResult result = client.changePositionLeverage("USDT", true);
        log.info("{}", result);
    }


    @Test
    public void riskSetting() {
        StringResult result = client.riskSetting("USDT", "BTCUSD", 10);
        log.info("{}", result);
    }

    @Test
    public void amendOrder() {
        String orderId = "O101-20200904-081706-418-0885";
        StringResult result = client.amendOrder(orderId, null, "330",
            null, null, null,null,null);
        log.info("{}", result);
    }
}
