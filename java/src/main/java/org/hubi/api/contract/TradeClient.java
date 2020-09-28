package org.hubi.api.contract;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import okhttp3.Response;
import org.hubi.api.AccessKeys;
import org.hubi.api.BaseClient;
import org.hubi.api.HttpClient;
import org.hubi.api.contract.domain.AssetAccount;
import org.hubi.api.contract.domain.Order;
import org.hubi.api.contract.domain.Position;
import org.hubi.api.contract.domain.StringResult;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 合约交易客户端
 * <p>
 * 参考：https://developer.hubi.pub/v/v2/contract/trade-rest-api
 */
public class TradeClient extends BaseClient {

    /**
     * 构造请求客户端
     */
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
     * 获取订单列表
     */
    public List<Order> queryOrdersPro() {
        String url = "/api/futures/query_orders_pro";
        Response response = this.getClient().get(url);
        try {
            return stringToList(response, Order.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取订单列表
     */
    public List<Order> queryOrdersPro(long timestamp) {
        String url = "/api/futures/query_orders_pro";
        Map<String, String> parameters = ImmutableMap.<String, String>builder()
            .put("timestamp", String.valueOf(timestamp))
            .build();

        Response response = this.getClient().get(url, parameters);
        try {
            return stringToList(response, Order.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取订单详情
     */
    public Order queryOrderById(String id) {
        String url = "/api/futures/query_order_by_id";
        Map<String, String> parameters = ImmutableMap.<String, String>builder()
            .put("order_no", id)
            .build();

        Response response = this.getClient().get(url, parameters);
        try {
            return stringToObject(response, Order.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询未完成的订单
     */
    public List<Order> queryActiveOrders(String symbol) {
        String url = "/api/futures/query_active_orders";
        Map<String, String> parameters = ImmutableMap.<String, String>builder()
            .put("symbol", symbol)
            .build();

        Response response = this.getClient().get(url, parameters);
        try {
            return stringToList(response, Order.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取合约资产
     */
    public List<AssetAccount> queryAccounts() {
        String url = "/api/futures/query_accounts";
        Response response = this.getClient().get(url);
        try {
            return stringToList(response, AssetAccount.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取仓位
     */
    public List<Position> queryPosition() {
        String url = "/api/futures/query_position";
        Response response = this.getClient().get(url);
        try {
            return stringToList(response, Position.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public enum TradeDirection {
        BUY,
        SELL
    }

    public enum OrderType {
        LIMIT,
        MARKET
    }

    public enum TriggerType {
        LAST,
        INDEX
    }


    /**
     * 下单
     */
    public StringResult enterMarketOrder(
        String currency, String symbol, Boolean openPosition, TradeDirection direction, int quantity) {
        return this.enterOrder(currency, symbol, openPosition, direction, quantity, OrderType.MARKET,
            null, null, null, null, null, null, null);
    }

    /**
     * 下单
     */
    public StringResult enterLimitOrder(
        String currency, String symbol, Boolean openPosition, TradeDirection direction, int quantity, BigDecimal price) {
        return this.enterOrder(currency, symbol, openPosition, direction, quantity, OrderType.LIMIT, price,
            null, null, null, null, null, null);
    }

    /**
     * 下单
     */
    public StringResult enterOrder(
        String currency, String symbol, Boolean openPosition, TradeDirection direction,
        int quantity, OrderType orderType, BigDecimal price,
        BigDecimal stopLossPrice, BigDecimal stopWinPrice,
        OrderType stopWinType, BigDecimal trailingStop, BigDecimal triggerPrice,
        TriggerType triggerType) {
        String url = "/api/futures/enter_order";
        ImmutableMap.Builder<String, String> builder = ImmutableMap.<String, String>builder()
            .put("coin_code", currency)
            .put("symbol", symbol)
            .put("open_position", openPosition.toString())
            .put("quantity", String.valueOf(quantity))
            .put("trade_direction", direction.name())
            .put("order_type", orderType.name());

        if (price != null) {
            builder.put("price", price.toPlainString());
        }
        if (stopLossPrice != null) {
            builder.put("stop_loss_price", stopLossPrice.toPlainString());
        }
        if (trailingStop != null) {
            builder.put("trailing_stop", trailingStop.toPlainString());
        }
        if (stopWinPrice != null) {
            builder.put("stop_win_price", stopWinPrice.toPlainString());
        }
        if (stopWinType != null) {
            builder.put("stop_win_type", stopWinType.name());
        }
        if (triggerPrice != null) {
            builder.put("trigger_price", triggerPrice.toPlainString());
        }
        if (triggerType != null) {
            builder.put("trigger_type", triggerType.name());
        }

        Response response = this.getClient().postForm(url, builder.build());
        try {
            return stringToObject(response, StringResult.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 取消订单
     */
    public StringResult cancelOrder(String orderNo) {
        String url = "/api/futures/cancel_order";
        Map<String, String> parameters = ImmutableMap.<String, String>builder()
            .put("order_no", orderNo)
            .build();

        Response response = this.getClient().postForm(url, parameters);
        try {
            return stringToObject(response, StringResult.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量取消订单
     */
    public StringResult cancelOrder(List<String> orderNos) {
        String url = "/api/futures/cancel_order_batch";
        Map<String, String> parameters = ImmutableMap.<String, String>builder()
            .put("order_nos", String.join(",", orderNos))
            .build();

        Response response = this.getClient().postForm(url, parameters);
        try {
            return stringToObject(response, StringResult.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public enum PositionType {
        LONG,
        SHORT
    }

    /**
     * 仓位全平
     */
    public StringResult closePosition(String currency, String symbol, PositionType positionType) {
        String url = "/api/futures/close_position";
        Map<String, String> parameters = ImmutableMap.<String, String>builder()
            .put("coin_code", currency)
            .put("symbol", symbol)
            .put("position_type", positionType.name())
            .build();

        Response response = this.getClient().postForm(url, parameters);
        try {
            return stringToObject(response, StringResult.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 切换为全仓模式
     */
    public StringResult switchToCross(String currency) {
        String url = "/api/futures/switch_to_cross";
        Map<String, String> parameters = ImmutableMap.<String, String>builder()
            .put("coin_code", currency)
            .build();

        Response response = this.getClient().postForm(url, parameters);
        try {
            return stringToObject(response, StringResult.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 切换单双向持仓模式
     */
    public StringResult changePositionLeverage(String currency, boolean twoSidePosition) {
        String url = "/api/futures/change_position_leverage";
        Map<String, String> parameters = ImmutableMap.<String, String>builder()
            .put("coin_code", currency)
            .put("two_side_position", String.valueOf(twoSidePosition))
            .build();

        Response response = this.getClient().postForm(url, parameters);
        try {
            return stringToObject(response, StringResult.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 切换持仓杠杆
     */
    public StringResult riskSetting(String currency, String symbol, int leverage) {
        String url = "/api/futures/risk_setting";
        Map<String, String> parameters = ImmutableMap.<String, String>builder()
            .put("coin_code", currency)
            .put("symbol", symbol)
            .put("leverage", String.valueOf(leverage))
            .build();

        Response response = this.getClient().postForm(url, parameters);
        try {
            return stringToObject(response, StringResult.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改合约订单
     */
    public StringResult amendOrder(String orderNo,
                                   String quantity,
                                   String price,
                                   String triggerPrice,
                                   String stopLossPrice,
                                   String trailingStop,
                                   String stopWinPrice,
                                   String stopWinType) {
        String url = "/api/futures/amend_order";
        ImmutableMap.Builder<String, String> builder = ImmutableMap.<String, String>builder()
            .put("order_no", orderNo);
        if (trailingStop != null) {
            builder.put("trailing_stop", trailingStop);
        }
        if (stopWinPrice != null) {
            builder.put("stop_win_price", stopWinPrice);
        }
        if (!Strings.isNullOrEmpty(stopWinType)) {
            builder.put("stop_win_type", stopWinType);
        }
        if (triggerPrice != null) {

            if (quantity != null) {
                builder.put("quantity", quantity);
            }
            if (price != null) {
                builder.put("price", price);
            }
            if (stopLossPrice != null) {
                builder.put("stop_loss_price", stopLossPrice);
            }
            builder.put("trigger_price", triggerPrice);
        }

        Response response = this.getClient().postForm(url, builder.build());
        try {
            return stringToObject(response, StringResult.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改合约订单
     */
    public StringResult riskSetting(String currency,
                                    String symbol,
                                    String positionType,
                                    String addDeposit,
                                    String price,
                                    String triggerPrice,
                                    String stopLossPrice,
                                    String trailingStop,
                                    String stopWinPrice,
                                    String stopWinType) {
        String url = "/api/futures/risk_setting";
        ImmutableMap.Builder<String, String> builder = ImmutableMap.<String, String>builder()
            .put("coin_code", currency)
            .put("symbol", symbol)
            .put("position_type", positionType);

        if (addDeposit != null) {
            builder.put("add_deposit", price);
        }
        if (price != null) {
            builder.put("price", price);
        }
        if (stopLossPrice != null) {
            builder.put("stop_loss_price", stopLossPrice);
        }
        if (trailingStop != null) {
            builder.put("trailing_stop", trailingStop);
        }
        if (stopWinPrice != null) {
            builder.put("stop_win_price", stopWinPrice);
        }
        if (!Strings.isNullOrEmpty(stopWinType)) {
            builder.put("stop_win_type", stopWinType);
        }
        if (triggerPrice != null) {
            builder.put("trigger_price", triggerPrice);
        }

        Response response = this.getClient().postForm(url, builder.build());
        try {
            return stringToObject(response, StringResult.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
