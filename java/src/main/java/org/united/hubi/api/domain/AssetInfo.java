package org.united.hubi.api.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AssetInfo {
    private String coinCode;
    private BigDecimal availableAmount;
    private BigDecimal frozenAmount;
    private BigDecimal lockedAmount;
    private BigDecimal totalAmount;
}
