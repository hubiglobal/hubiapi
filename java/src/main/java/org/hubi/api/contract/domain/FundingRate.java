package org.hubi.api.contract.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.hubi.api.IsoLocalDateTimeDeserializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FundingRate {
    private String symbol;
    //2020-09-28T07:10:00.000+0000
    @JsonDeserialize(using = IsoLocalDateTimeDeserializer.class)
    private LocalDateTime date;
    private BigDecimal rate;
}
