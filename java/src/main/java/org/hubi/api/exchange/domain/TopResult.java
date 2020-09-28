package org.hubi.api.exchange.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TopResult {
    private String no;
    private String coinCode;
    private String priceCoinCode;
    private Entrust.EntrustWay way;
    private Entrust.Direction direction;
    private BigDecimal price;
    private BigDecimal count;
    private long millisTime;
    private BigDecimal surplusCount;
    private BigDecimal transactionCount;
    private BigDecimal canceledAmount;
    private Entrust.Status status;
}
