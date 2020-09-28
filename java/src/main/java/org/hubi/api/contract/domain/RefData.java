package org.hubi.api.contract.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RefData {
    private BigDecimal lotSize;
    private BigDecimal rate;
    private String symbol;
    private BigDecimal tick;
    private String type;
}
