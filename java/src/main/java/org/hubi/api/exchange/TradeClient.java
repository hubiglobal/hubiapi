package org.hubi.api.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.hubi.api.AccessKeys;
import org.hubi.api.BaseClient;
import org.hubi.api.HttpClient;
import org.hubi.api.PageInfo;
import org.hubi.api.exchange.domain.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
public class TradeClient extends BaseClient {
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT
        = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public TradeClient(AccessKeys accessKeys) {
        this(accessKeys, new ObjectMapper());
    }

    public TradeClient(AccessKeys accessKeys, ObjectMapper objectMapper) {
        super(accessKeys, objectMapper);
    }

    public TradeClient(HttpClient httpClient) {
        this(httpClient, new ObjectMapper());
    }

    public TradeClient(HttpClient httpClient, ObjectMapper objectMapper) {
        super(httpClient, objectMapper);
    }


    /**
     * 用户信息
     */
    public UserInfo user() {
        String url = "/api/user";
        Response response = this.getClient().get(url);
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
        Response response = this.getClient().get(url);
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
        Response response = this.getClient().get(url);
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
            Response response = this.getClient().get(url, parameters);
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
            Response response = this.getClient().get(url, parameters);
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
            Response response = this.getClient().postForm(url, parameters);
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
            Response response = this.getClient().postForm(url, parameters);
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
            Response response = this.getClient().postForm(url, parameters);
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
            Response response = this.getClient().put(url, parameters);
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
            Response response = this.getClient().postForm(url, parameters);
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
            Response response = this.getClient().postForm(url, parameters);
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
        Map<String, String> parameters = ImmutableMap.<String, String>builder()
            .put("size", String.valueOf(size))
            .build();
        String url = "/api/connect/public/market/trade/" + symbol;
        try {
            Response response = this.getClient().get(url, parameters);
            return stringToList(response, TradeInfo.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
