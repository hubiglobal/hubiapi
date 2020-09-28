package org.hubi.api.contract.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Depth {

    private List<Item> buyDepth;
    private List<Item> sellDepth;

    @Data
    public static class Item {
        private BigDecimal price;
        private BigDecimal qty;
        private int count;
        private int iceCount;
    }
}
