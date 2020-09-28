package org.hubi.api.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.hubi.api.AccessKeys;
import org.hubi.api.BaseClient;
import org.hubi.api.HttpClient;
import org.hubi.api.exchange.domain.DepthInfo;
import org.hubi.api.exchange.domain.TradeInfo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

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
     * 行情
     *
     * @param symbol
     */
    public DepthInfo depth(String symbol) {
        String url = "/api/connect/public/market/depth/" + symbol;
        try {
            Response response = this.getClient().get(url);
            return stringToObject(response, DepthInfo.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 最新成交
     */
    public List<TradeInfo> trade(String symbol, int size) {
        String url = "/api/connect/public/market/trade/" + symbol;
        Map<String, String> parameters = ImmutableMap.<String, String>builder()
            .put("size", String.valueOf(size))
            .build();
        try {
            Response response = this.getClient().get(url, parameters);
            return stringToList(response, TradeInfo.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
