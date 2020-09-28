package org.hubi.api.exchange.domain;

import lombok.Data;

import java.util.List;

@Data
public class DepthInfo {
    private long timestamp;
    private String last_strike_price;
    private List<Item> asks;
    private List<Item> bids;

    @Data
    public static class Item {
        private String price;
        private String volume;
    }
}
