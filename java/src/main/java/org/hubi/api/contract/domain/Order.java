package org.hubi.api.contract.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.hubi.api.IsoLocalDateTimeDeserializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order {

    //2020-06-18T19:15:51.000+0000
    @JsonDeserialize(using = IsoLocalDateTimeDeserializer.class)
    private LocalDateTime created;

    //2020-06-18T19:15:51.000+0000
    @JsonDeserialize(using = IsoLocalDateTimeDeserializer.class)
    private LocalDateTime modified;

    private boolean active;

    private String currency;

    private BigDecimal fee;

    private BigDecimal feeRate;

    private String id;

    private boolean openPosition;

    private String orderId;

    private BigDecimal price;

    private BigDecimal qty;

    private String side;

    private String symbol;

    private BigDecimal avgPx;

    private BigDecimal cumQty;

    private boolean iceberg;

    private String ordStatus;

    private BigDecimal pnl;

    private BigDecimal showQty;

    private String source;

    private BigDecimal stopLossPrice;

    private BigDecimal stopWinPrice;

    private String stopWinType;

    private String tif;

    private BigDecimal trailingStop;

    private BigDecimal triggerPrice;

    private String triggerType;

    private String type;

}
