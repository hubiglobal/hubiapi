package org.hubi.api.contract;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.hubi.api.AccessKeys;
import org.hubi.api.BaseClient;
import org.hubi.api.HttpClient;
import org.hubi.api.contract.domain.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * 合约行情访问客户端
 * <p>
 * 参考： https://developer.hubi.pub/v/v2/contract/market-rest-api
 */
@Slf4j
public class MarketClient extends BaseClient {
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT
        = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public MarketClient(AccessKeys accessKeys) {
        this(accessKeys, new ObjectMapper());
    }

    public MarketClient(AccessKeys accessKeys, ObjectMapper objectMapper) {
        super(accessKeys, objectMapper);
    }

    public MarketClient(HttpClient httpClient) {
        this(httpClient, new ObjectMapper());
    }

    public MarketClient(HttpClient httpClient, ObjectMapper objectMapper) {
        super(httpClient, objectMapper);
    }

    /**
     * 获取币种
     */
    public List<RefData> refData() {
        String url = "/api/futures/public/ref_data";
        try {
            Response response = this.getClient().get(url);
            return stringToList(response, RefData.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取最新成交价
     */
    public Map<String, String> lastPrice() {
        String url = "/api/futures/public/last_price";
        try {
            Response response = this.getClient().get(url);
            return stringToObject(response, Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取市场深度
     */
    public Depth depth(String symbol) {
        String url = "/api/futures/public/depth/depth";
        Map<String, String> params = ImmutableMap.<String, String>builder()
            .put("symbol", symbol)
            .build();
        try {
            Response response = this.getClient().get(url, params);
            return stringToObject(response, Depth.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取最新成交记录
     */
    public List<Trade> trades(String symbol, String sequence) {
        String url = "/api/futures/public/depth/trades";
        ImmutableMap.Builder<String, String> params = ImmutableMap.<String, String>builder()
            .put("symbol", symbol)
            .put("sequence", sequence);
        try {
            Response response = this.getClient().get(url, params.build());
            return stringToList(response, Trade.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过下标索引获取历史 K 线
     */
    public List<Kline> klineByIndex(String symbol, String type, int step, long from) {
        String url = "/api/futures/public/kline/by_index";
        ImmutableMap.Builder<String, String> params = ImmutableMap.<String, String>builder()
            .put("symbol", symbol)
            .put("type", type)
            .put("step", String.valueOf(step))
            .put("from", String.valueOf(from));

        try {
            Response response = this.getClient().get(url, params.build());
            return stringToList(response, Kline.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过时间索引获取历史 K 线
     */
    public List<Kline> klineByTime(String symbol, String type, int step, long from) {
        String url = "/api/futures/public/kline/by_time";
        ImmutableMap.Builder<String, String> params = ImmutableMap.<String, String>builder()
            .put("symbol", symbol)
            .put("type", type)
            .put("step", String.valueOf(step))
            .put("from", String.valueOf(from));

        try {
            Response response = this.getClient().get(url, params.build());
            return stringToList(response, Kline.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取最新一根 K 线数据
     */
    public Kline klineLatest(String symbol, String type) {
        String url = "/api/futures/public/kline/latest";
        ImmutableMap.Builder<String, String> params = ImmutableMap.<String, String>builder()
            .put("symbol", symbol)
            .put("type", type);

        try {
            Response response = this.getClient().get(url, params.build());
            return stringToObject(response, Kline.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取资金费率
     */
    public List<FundingRate> fundingRate(String symbols) {
        String url = "/api/futures/public/kline/funding_rate";
        ImmutableMap.Builder<String, String> params = ImmutableMap.<String, String>builder()
            .put("symbols", symbols);

        try {
            Response response = this.getClient().get(url, params.build());
            return stringToList(response, FundingRate.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取最近24小时的成交统计数据
     */
    public List<TradeStats> tradeStatistics(String symbols) {
        String url = "/api/futures/public/kline/trade_statistics";
        ImmutableMap.Builder<String, String> params = ImmutableMap.<String, String>builder()
            .put("symbols", symbols);

        try {
            Response response = this.getClient().get(url, params.build());
            return stringToList(response, TradeStats.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取系统合约持仓量
     */
    public OpenInterest openInterest(String symbol) {
        String url = "/api/futures/public/kline/open_interest";
        ImmutableMap.Builder<String, String> params = ImmutableMap.<String, String>builder()
            .put("symbol", symbol);

        try {
            Response response = this.getClient().get(url, params.build());
            return stringToObject(response, OpenInterest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
