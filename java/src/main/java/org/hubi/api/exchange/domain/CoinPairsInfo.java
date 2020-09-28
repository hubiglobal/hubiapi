package org.hubi.api.exchange.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CoinPairsInfo {
    private String coinCode;
    private String coinParisStr;
    private List<Pair> coinPairs;

    @Data
    public static class Pair {
        /**
         * 交易币
         */
        private String coinCode;
        /**
         * 定份币
         */
        private String priceCoinCode;
        /**
         * 买方手续费率
         */
        private BigDecimal buyFeeRate;
        /**
         * 卖方手续费率
         */
        private BigDecimal sellFeeRate;
        /**
         * 限价单笔最大涨幅
         */
        private BigDecimal maxIncrease;
        /**
         * 限价单笔最大跌幅
         */
        private BigDecimal maxDecline;
        /**
         * 单笔最小下单量
         */
        private BigDecimal minOrderLimit;
        /**
         * 单笔最大下单量
         */
        private BigDecimal maxOrderLimit;
        /**
         * 交易对价格小数位
         */
        private BigDecimal pairsPricePrecision;
        /**
         * 交易对数量小数位
         */
        private BigDecimal pairsVolumePrecision;
        /**
         * 市价最小买入量
         */
        private BigDecimal marketBuyMin;
        /**
         * 市价最大买入量
         */
        private BigDecimal marketBuyMax;
        /**
         * 市价最小卖出量
         */
        private BigDecimal marketSellMin;
        /**
         * 市价最大卖出量
         */
        private BigDecimal marketSellMax;
        /**
         * 交易状态
         */
        private boolean tradeStatus;
        /**
         * 开盘价
         */
        private BigDecimal openPrice;
        /**
         * 市场分区
         */
        private String marketRegion;


    }
}
