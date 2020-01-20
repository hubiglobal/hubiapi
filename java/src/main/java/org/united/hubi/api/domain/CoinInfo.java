package org.united.hubi.api.domain;

import lombok.Data;

@Data
public class CoinInfo {
    private String id;
    /**
     * 货币代码
     */
    private String coinCode;
}
