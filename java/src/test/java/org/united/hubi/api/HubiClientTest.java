package org.united.hubi.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.united.hubi.api.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * @author HUBI
 */
@Slf4j
public class HubiClientTest {

    //https://www.hubi.com/zh/api/setting
    private static final String key = "******";
    private static final String secret = "******";
    private static final String accessToken = "******";

    private final HubiClient client = new HubiClient(
        new AccessKeys(key, secret, accessToken)
    );

    @Test
    public void user() {
        UserInfo info = client.user();
        log.info("{}", info);
    }

    @Test
    public void coinInfo() {
        List<CoinInfo> info = client.coinInfo();
        log.info("{}", info);
    }


    @Test
    public void coinPairsInfo() {
        List<CoinPairsInfo> info = client.coinPairsInfo();
        log.info("{}", info);
    }


    @Test
    public void asset() {
        PageInfo<AssetInfo> info = client.asset(0, 2);
        log.info("{}", info);
    }

    @Test
    public void fixed() {
        FixedResult result = client.fixed("ETH", "USDT", Entrust.Direction.SELL,
            new BigDecimal("110"), new BigDecimal("1"), "");
        log.info("{}", result);
    }

    @Test
    public void entrust() {
        Entrust result = client.entrust("139672783490678784");
        log.info("{}", result);
    }


    @Test
    public void order() {
        List<OrderInfo> result = client.order("139675489282326528");
        log.info("{}", result);
    }

    @Test
    public void top() {
        List<TopResult> result = client.top("ETH", "USDT", 100);
        log.info("{}", result);
    }

    @Test
    public void history() {
        LocalDate yesterday = LocalDate.now().plusDays(-1);
        Date begin = Date.from(yesterday.atStartOfDay(ZoneId.systemDefault()).toInstant());
        PageInfo<Entrust> result = client.history(0, 2,
            "ETH", "USDT", Entrust.Direction.BUY,
            begin, new Date(),
            false);
        log.info("{}", result);
    }


    @Test
    public void cancel() {
        CancelResult result = client.cancel("139669789713891328");
        log.info("{}", result);
    }

    @Test
    public void cancelBatch() {
        List<CancelResult> result = client.cancel("139669789713891328", "139669789713891328");
        log.info("{}", result);
    }

    @Test
    public void depth() {
        DepthInfo info = client.depth("ETH_USDT");
        log.info("{}", info);
    }

    @Test
    public void trade() {
        List<TradeInfo> info = client.trade("ETH_USDT", 2);
        log.info("{}", info);
    }
}
