package org.hubi.api.contract.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.hubi.api.IsoLocalDateTimeDeserializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TradeLedger {
    private String id;
    private BigDecimal amount;
    private BigDecimal balance;
    private BigDecimal qty;
    private String currency;
    //2020-09-29T04:00:00.000+0000
    @JsonDeserialize(using = IsoLocalDateTimeDeserializer.class)
    private LocalDateTime created;
}
