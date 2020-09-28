package org.hubi.api.contract.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Position {
    private String id;

    private String currency;

    private String symbol;

    private String side;

    private BigDecimal qty;

    private BigDecimal price;

    private boolean individualPosition;

    private BigDecimal closableQty;


    private BigDecimal pnlRate;

    private BigDecimal value;

    private BigDecimal positionLeverage;


    private BigDecimal stopLossPrice;

    private BigDecimal stopWinPrice;

    private BigDecimal stopWinType;

    private BigDecimal trailingStop;

    private BigDecimal trailingStopPrice;

    private BigDecimal pnl;

    private BigDecimal urPnL;

    private BigDecimal liquidationPrice;

}
