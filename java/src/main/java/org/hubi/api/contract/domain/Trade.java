package org.hubi.api.contract.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.hubi.api.IsoLocalDateTimeDeserializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Trade {
    private String id;
    private String symbol;
    private BigDecimal price;
    private BigDecimal qty;
    private boolean buyActive = false;
    //2020-09-03T10:12:21.730+0000
    @JsonDeserialize(using = IsoLocalDateTimeDeserializer.class)
    private LocalDateTime timestamp;
}
