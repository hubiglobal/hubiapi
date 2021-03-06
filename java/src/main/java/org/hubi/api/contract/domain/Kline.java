package org.hubi.api.contract.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.hubi.api.IsoLocalDateTimeDeserializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Kline {
    private String symbol;
    //2020-09-28T07:10:00.000+0000
    @JsonDeserialize(using = IsoLocalDateTimeDeserializer.class)
    private LocalDateTime keyTime;
    private BigDecimal close;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal open;
    private BigDecimal rate;
    private BigDecimal source;
    //2020-09-28T07:10:00.000+0000
    @JsonDeserialize(using = IsoLocalDateTimeDeserializer.class)
    private LocalDateTime timeStamp;
    private BigDecimal turnover;
    private BigDecimal volume;
}
