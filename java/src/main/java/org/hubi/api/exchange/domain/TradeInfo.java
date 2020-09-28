package org.hubi.api.exchange.domain;

import lombok.Data;

@Data
public class TradeInfo {
    private String symbol;
    private String id;
    private String price;
    private long time;
    private String type;
    private String volume;

}
