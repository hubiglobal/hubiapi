package org.united.hubi.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.united.hubi.api.domain.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@Slf4j
public class HubiClient {
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT
        = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    private static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone("GMT+8");

    /**
     * 构造请求客户端
     */
    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public HubiClient(AccessKeys accessKeys) {
        this(accessKeys, new ObjectMapper());
    }

    public HubiClient(AccessKeys accessKeys, ObjectMapper objectMapper) {
        this.client = new HttpClient(accessKeys);
        this.objectMapper = objectMapper;
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.objectMapper.setDateFormat(DATE_FORMAT.get());
        this.objectMapper.setTimeZone(DEFAULT_TIME_ZONE);
    }

    private RuntimeException exception(Response response) throws IOException {
        JavaType jt = objectMapper.getTypeFactory().constructMapType(Map.class, String.class, String.class);
        Map<String, String> map = objectMapper.readValue(response.body().string(), jt);
        if (map.containsKey("message")) {
            return new RuntimeException(map.get("message"));
        }
        return new RuntimeException(response.body().string());
    }

    private <T> T stringToObject(Response response, Class<T> valueType) throws IOException {
        if (response.isSuccessful()) {
            String s = response.body().string();
            log.debug(s);
            return objectMapper.readValue(s, valueType);
        } else {
            throw exception(response);
        }

    }

    private <T> T stringToObject(Response response, Class<T> parametrized, Class<?>... parameterClasses) throws IOException {
        if (response.isSuccessful()) {
            JavaType jt = objectMapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
            String s = response.body().string();
            log.debug(s);
            return objectMapper.readValue(s, jt);
        } else {
            throw exception(response);
        }
    }


    private <T> List<T> stringToList(Response response, Class<T> elementType) throws IOException {
        if (response.isSuccessful()) {
            JavaType jt = objectMapper.getTypeFactory().constructCollectionType(List.class, elementType);
            String s = response.body().string();
            log.debug(s);
            return objectMapper.readValue(s, jt);
        } else {
            throw exception(response);
        }

    }

    /**
     * 用户信息
     */
    public UserInfo user() {
        String url = "/api/user";
        Response response = this.client.get(url);
        try {
            return stringToObject(response, UserInfo.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 币种信息
     */
    public List<CoinInfo> coinInfo() {
        String url = "/api/coin/coin_base_info/simple";
        Response response = this.client.get(url);
        try {
            return stringToList(response, CoinInfo.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 交易对信息
     */
    public List<CoinPairsInfo> coinPairsInfo() {
        String url = "/api/coin/coin_pairs_param/pairses";
        Response response = this.client.get(url);
        try {
            return stringToList(response, CoinPairsInfo.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询未完全成交的委托
     */
    public Entrust entrust(String entrustNumber) {
        Map<String, String> parameters = ImmutableMap.<String, String>builder()
            .put("entrust_number", entrustNumber)
            .build();

        String url = "/api/entrust/info";
        try {
            Response response = this.client.get(url, parameters);
            return stringToObject(response, Entrust.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<OrderInfo> order(String entrustNumber) {
        Map<String, String> parameters = ImmutableMap.<String, String>builder()
            .put("entrust_number", entrustNumber)
            .build();

        String url = "/api/entrust/order/info";
        try {
            Response response = this.client.get(url, parameters);
            return stringToList(response, OrderInfo.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询未完全成交的委托
     */
    public List<TopResult> top(String coinCode, String priceCoinCode, int top) {
        Map<String, String> parameters = ImmutableMap.<String, String>builder()
            .put("coin_code", coinCode)
            .put("price_coin_code", priceCoinCode)
            .put("top", String.valueOf(top))
            .build();

        String url = "/api/entrust/current/top";
        try {
            Response response = this.client.postForm(url, parameters);
            return stringToList(response, TopResult.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询历史委托
     */
    public PageInfo<Entrust> history(int page, int size,
                                     String coinCode, String priceCoinCode, Entrust.Direction direction,
                                     Date begin, Date end,
                                     boolean filterCanceled) {
        Map<String, String> parameters = ImmutableMap.<String, String>builder()
            .put("page", String.valueOf(page))
            .put("size", String.valueOf(size))
            .put("coin_code", coinCode)
            .put("price_coin_code", priceCoinCode)
            .put("direction", direction.name())
            .put("begin_time", DATE_FORMAT.get().format(begin))
            .put("end_time", DATE_FORMAT.get().format(end))
            .put("filter_canceled", String.valueOf(filterCanceled))
            .build();
        String url = "/api/entrust/exchange/slice/history/user";

        try {
            Response response = this.client.postForm(url, parameters);
            return stringToObject(response, PageInfo.class, Entrust.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 下单
     */
    public FixedResult fixed(String coinCode, String priceCoinCode, Entrust.Direction direction,
                             BigDecimal entrustPrice, BigDecimal entrustCount,
                             String tradePassword) {
        Map<String, String> parameters = ImmutableMap.<String, String>builder()
            .put("coin_code", coinCode)
            .put("price_coin_code", priceCoinCode)
            .put("direction", direction.name())
            .put("entrust_price", entrustPrice.toPlainString())
            .put("entrust_count", entrustCount.toPlainString())
            .put("trade_password", tradePassword)
            .build();

        String url = "/api/engine/entrust/fix";
        try {
            Response response = this.client.postForm(url, parameters);
            return stringToObject(response, FixedResult.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 撤销
     */
    public CancelResult cancel(String entrustNumbers) {
        Map<String, String> parameters = ImmutableMap.<String, String>builder()
            .put("entrust_numbers", entrustNumbers)
            .build();
        String url = "/api/engine/entrust/cancel/v1";
        try {
            Response response = this.client.put(url, parameters);
            return stringToObject(response, CancelResult.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Joiner ARRAY_STRING_JOINER = Joiner.on(',');

    /**
     * 撤销
     */
    public List<CancelResult> cancel(String... entrustNumbers) {
        Map<String, String> parameters = ImmutableMap.<String, String>builder()
            .put("entrust_numbers", ARRAY_STRING_JOINER.join(entrustNumbers))
            .build();
        String url = "/api/engine/entrust/cancel/batch/v1";
        try {
            Response response = this.client.postForm(url, parameters);
            return stringToList(response, CancelResult.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 资产查询
     */
    public PageInfo<AssetInfo> asset(int page, int size) {
        Map<String, String> parameters = ImmutableMap.<String, String>builder()
            .put("page", String.valueOf(page))
            .put("size", String.valueOf(size))
            .build();

        String url = "/api/asset/user/customer_asset_info/additional";
        try {
            Response response = this.client.postForm(url, parameters);
            return stringToObject(response, PageInfo.class, AssetInfo.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 行情
     *
     * @param symbol
     */
    public DepthInfo depth(String symbol) {
        String url = "/api/connect/public/market/depth/" + symbol;
        try {
            Response response = this.client.get(url);
            return stringToObject(response, DepthInfo.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 最新成交
     */
    public List<TradeInfo> trade(String symbol, int size) {
        Map<String, String> parameters = ImmutableMap.<String, String>builder()
            .put("size", String.valueOf(size))
            .build();
        String url = "/api/connect/public/market/trade/" + symbol;
        try {
            Response response = this.client.get(url, parameters);
            return stringToList(response, TradeInfo.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
