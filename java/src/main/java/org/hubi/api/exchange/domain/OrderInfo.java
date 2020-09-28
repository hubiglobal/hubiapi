package org.hubi.api.exchange.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderInfo {

    private String custId;
    private String entrustNum;
    private String orderNumber;

    /**
     * 交易币
     */
    private String coinCode;

    /**
     * 定价币
     */
    private String priceCoinCode;

    /**
     * 买卖方向
     */
    private Entrust.Direction direction;
    /**
     * 委托价格
     */
    private BigDecimal entrustPrice;
    /**
     * 委托数量
     */
    private BigDecimal entrustCount;
    /**
     * 委托总量
     */
    private BigDecimal entrustSum;
    /**
     * 交易手续费率
     */
    private BigDecimal transactionFeeRate;

    /**
     * 交易手续费
     */
    private BigDecimal transactionFee;

    /**
     * 成交总金额
     */
    private BigDecimal transactionSum;

}
