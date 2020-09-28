package org.hubi.api.contract.domain;

import lombok.Data;

@Data
public class AssetAccount {
    private String currency;
    private String cash;
    private String withdrawableCash;
    private String frozenCash;
    private String urPnl;
}
