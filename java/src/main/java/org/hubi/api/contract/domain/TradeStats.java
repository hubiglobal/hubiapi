package org.hubi.api.contract.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradeStats {
    private String symbol;
    private BigDecimal maxPrice;
    private BigDecimal minPrice;
    private BigDecimal priceChange;
    private BigDecimal priceChangeRatio;
    private BigDecimal lastPrice;
    private BigDecimal turnover;
    private BigDecimal volume;
}
