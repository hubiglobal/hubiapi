package org.united.hubi.api.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Entrust {

    private String id;
    /**
     * 委托单号
     */
    private String entrustNumber;

    /**
     * 下单用户ID
     */
    private String customerId;

    /**
     * 交易币
     */
    private String coinCode;

    /**
     * 定价币
     */
    private String priceCoinCode;

    /**
     * 买卖方向
     */
    private Direction direction;
    /**
     * 委托价格
     */
    private BigDecimal entrustPrice;
    /**
     * 委托数量
     */
    private BigDecimal entrustCount;
    /**
     * 委托总量
     */
    private BigDecimal entrustSum;
    /**
     * 委托时间
     */
    private Date entrustTime;
    /**
     * 下单类型（限价，市价，止盈止损）
     */
    private EntrustWay entrustWay;
    /**
     * 剩余委托数量
     */
    private BigDecimal surplusCount;
    /**
     * 交易手续费率
     */
    private BigDecimal transactionFeeRate;

    /**
     * 交易手续费
     */
    private BigDecimal transactionFee;

    /**
     * 成交总金额
     */
    private BigDecimal transactionSum;

    /**
     * 成交平均价
     */
    private BigDecimal processedPrice;

    private Status status;

    public enum EntrustWay {
        /**
         * 限价
         */
        FIXED,
        /**
         * 市价
         */
        MARKET,
        /**
         * 止盈止损
         */
        STOP_LOSS

    }

    public enum Status {
        /**
         * 提交中
         **/
        SUBMITTING(0),
        /**
         * 已提交
         **/
        SUBMITTED(1),
        /**
         * 提交失败
         **/
        SUBMITTED_FAIL(2),
        /**
         * 部分成交
         **/
        PARTIAL_FILLED(3),
        /**
         * 部分成交部分撤销
         **/
        PARTIAL_CANCELED(4),
        /**
         * 完全成交
         **/
        FILLED(5),
        /**
         * 已撤销
         **/
        CANCELED(6);

        private int order;

        Status(int order) {
            this.order = order;
        }

        public int getOrder() {
            return order;
        }
    }


    public enum Direction {
        /**
         * 买
         */
        BUY,
        /**
         * 卖
         */
        SELL
    }

}
