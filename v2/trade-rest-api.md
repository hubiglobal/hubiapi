# 交易 REST API

{% api-method method="get" host="https://api.hubi.com" path="/api/futures/query\_orders\_pro" %}
{% api-method-summary %}
获取订单列表\(增量\)
{% endapi-method-summary %}

{% api-method-description %}
返回上次请求到当前时间之间的订单
{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-query-parameters %}
{% api-method-parameter name="timestamp" type="integer" required=false %}
时间戳
{% endapi-method-parameter %}
{% endapi-method-query-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
[
    {
      "avgPx": 0,
      "created": "2020-09-02T06:52:05.587Z",
      "cumQty": 0,
      "currency": "string",
      "fee": 0,
      "iceberg": true,
      "id": "string",
      "modified": "2020-09-02T06:52:05.587Z",
      "openPosition": true,
      "ordStatus": "NEW",
      "pnl": 0,
      "price": 0,
      "qty": 0,
      "showQty": 0,
      "side": "Buy",
      "source": "Normal",
      "stopLossPrice": 0,
      "stopWinPrice": 0,
      "stopWinType": "Limit",
      "symbol": "string",
      "tif": "DAY",
      "trailingStop": 0,
      "triggerPrice": 0,
      "triggerType": "LAST",
      "type": "Limit",
      "uid": "string"
    }
]
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| id | String | 订单id，eg: O101-20190910-033720-922-1879 |
| currency | String | 交易币种 |
| symbol | String | 合约名称 |
| created | Date | 订单创建时间 |
| modified | Date | 订单发生变动时间 |
| side | String | 订单方向，Buy/Sell |
| type | String | 订单类型，Limit/Market |
| price | Double | 订单价格 |
| qty | Double | 订单数量 |
| openPosition | Boolean | 开仓/平仓单，开仓为 true，平仓为false |
| cumQty | Double | 已成交数量 |
| avgPx | Double | 成交均价 |
| ordStatus | String | 订单状态，NEW - 新单；PARTIALLY\_FILLED - 部分成交；FILLED - 全部成交；CANCELED - 已撤；REJECTED - 已拒；WAITING - 等待（条件单） |
| iceberg | Boolean | 是否为冰山单 |
| showQty | Double | 冰山单显示数量 |
| source | String | 订单来源，Normal - 正常下单；Conditional - 条件单；StopWin - 止盈单；StopLoss - 止损单；TrailingStop - 追踪止损单；SysRisk - 爆仓单 |
| stopLossPrice | Double | 止损价 |
| stopWinPrice | Double | 止盈价 |
| stopWinType | String | 止盈类型，Limit/Market |
| trailingStop | Double | 追踪止损 |
| triggerPrice | Double | 条件单触发价 |
| triggerType | String | 条件单触发价类型，LAST - 最后成交价，INDEX - 标记价 |

{% api-method method="get" host="https://api.hubi.com" path="/api/futures/query\_order\_by\_id" %}
{% api-method-summary %}
获取订单详情
{% endapi-method-summary %}

{% api-method-description %}

{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-query-parameters %}
{% api-method-parameter name="order\_no" type="string" required=true %}
订单号
{% endapi-method-parameter %}
{% endapi-method-query-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
{
    "avgPx": 0,
    "created": "2020-09-02T07:17:58.928Z",
    "cumQty": 0,
    "currency": "string",
    "fee": 0,
    "iceberg": true,
    "id": "string",
    "modified": "2020-09-02T07:17:58.928Z",
    "openPosition": true,
    "ordStatus": "NEW",
    "pnl": 0,
    "price": 0,
    "qty": 0,
    "showQty": 0,
    "side": "Buy",
    "source": "Normal",
    "stopLossPrice": 0,
    "stopWinPrice": 0,
    "stopWinType": "Limit",
    "symbol": "string",
    "tif": "DAY",
    "trailingStop": 0,
    "triggerPrice": 0,
    "triggerType": "LAST",
    "type": "Limit",
    "uid": "string"
}
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| id | String | 订单id，eg: O101-20190910-033720-922-1879 |
| currency | String | 交易币种 |
| symbol | String | 合约名称 |
| created | Date | 订单创建时间 |
| modified | Date | 订单发生变动时间 |
| side | String | 订单方向，Buy/Sell |
| type | String | 订单类型，Limit/Market |
| price | Double | 订单价格 |
| qty | Double | 订单数量 |
| openPosition | Boolean | 开仓/平仓单，开仓为 true，平仓为false |
| cumQty | Double | 已成交数量 |
| avgPx | Double | 成交均价 |
| ordStatus | String | 订单状态，NEW - 新单；PARTIALLY\_FILLED - 部分成交；FILLED - 全部成交；CANCELED - 已撤；REJECTED - 已拒；WAITING - 等待（条件单） |
| iceberg | Boolean | 是否为冰山单 |
| showQty | Double | 冰山单显示数量 |
| source | String | 订单来源，Normal - 正常下单；Conditional - 条件单；StopWin - 止盈单；StopLoss - 止损单；TrailingStop - 追踪止损单；SysRisk - 爆仓单 |
| stopLossPrice | Double | 止损价 |
| stopWinPrice | Double | 止盈价 |
| stopWinType | Double | 止盈类型，Limit/Market |
| trailingStop | Double | 追踪止损 |
| triggerPrice | Double | 条件单触发价 |
| triggerType | String | 条件单触发价类型，LAST - 最后成交价，INDEX - 标记价 |

{% api-method method="get" host="https://api.hubi.com" path="/api/futures/query\_accounts" %}
{% api-method-summary %}
获取资产
{% endapi-method-summary %}

{% api-method-description %}

{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
[
    {
      "cash": 0,
      "cashAvailable": 0,
      "currency": "string",
      "factLeverage": 0,
      "frozenCash": 0,
      "uid": "string",
      "urPnl": 0,
      "withdrawableCash": 0
    }
]
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| currency | String | 币种 |
| cash | Double | 总资金 |
| withdrawableCash | Double | 可提资金 |
| frozenCash | Double | 冻结资金 |
| urPnl | Double | 账户浮动盈亏 |

{% api-method method="get" host="https://api.hubi.com" path="/api/futures/query\_position" %}
{% api-method-summary %}
获取仓位
{% endapi-method-summary %}

{% api-method-description %}

{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
[
    {
      "closableQty": 0,
      "created": "2020-09-02T07:26:54.675Z",
      "currency": "string",
      "deposit": 0,
      "individualPosition": true,
      "lastPrice": 0,
      "liquidationPrice": 0,
      "pnl": 0,
      "pnlRate": 0,
      "positionLeverage": 0,
      "price": 0,
      "qty": 0,
      "side": "Long",
      "stopLossPrice": 0,
      "stopWinPrice": 0,
      "stopWinType": "Limit",
      "symbol": "string",
      "trailingStop": 0,
      "trailingStopPrice": 0,
      "uid": "string",
      "urPnL": 0,
      "value": 0
    }
  ]
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| currency | String | 交易币种 |
| symbol | String | 合约名称 |
| side | String | 持仓方向，Long - 多仓，Short - 空仓 |
| qty | Double | 持仓数量 |
| individualPosition | Boolean | 是否逐仓，true - 逐仓，false - 全仓 |
| price | Double | 持仓价格 |
| closableQty | Double | 可平数量 |
| pnlRate | Double | 浮动盈亏比例 |
| value | Double | 持仓价值 |
| positionLeverage | Double | 杠杆 |
| stopLossPrice | Double | 止损价设定 |
| stopWinPrice | Double | 止盈价设定 |
| stopWinType | String | 止盈类型，Limit/Market |
| trailingStop | Double | 追踪止损价距 |
| trailingStopPrice | Double | 根据追踪止损算出的止损价 |
| pnl | Double | 已实现盈亏 |
| urPnL | Double | 浮动盈亏 |
| liquidationPrice | Double | 爆仓价 |
| deposit | Double | 保证金 |

{% api-method method="get" host="https://api.hubi.com" path="/api/futures/query\_active\_orders" %}
{% api-method-summary %}
获取未完成的订单
{% endapi-method-summary %}

{% api-method-description %}

{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-query-parameters %}
{% api-method-parameter name="symbol" type="string" required=false %}
合约名字，BTCUSD, BCHUSD...
{% endapi-method-parameter %}
{% endapi-method-query-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
[
    {
      "avgPx": 0,
      "created": "2020-09-02T07:49:51.338Z",
      "cumQty": 0,
      "currency": "string",
      "fee": 0,
      "iceberg": true,
      "id": "string",
      "modified": "2020-09-02T07:49:51.338Z",
      "openPosition": true,
      "ordStatus": "NEW",
      "pnl": 0,
      "price": 0,
      "qty": 0,
      "showQty": 0,
      "side": "Buy",
      "source": "Normal",
      "stopLossPrice": 0,
      "stopWinPrice": 0,
      "stopWinType": "Limit",
      "symbol": "string",
      "tif": "DAY",
      "trailingStop": 0,
      "triggerPrice": 0,
      "triggerType": "LAST",
      "type": "Limit",
      "uid": "string"
    }
]
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| id | String | 订单id，eg: O101-20190910-033720-922-1879 |
| currency | String | 交易币种 |
| symbol | String | 合约名称 |
| created | Date | 订单创建时间 |
| modified | Date | 订单发生变动时间 |
| side | String | 订单方向，Buy/Sell |
| type | String | 订单类型，Limit/Market |
| price | Double | 订单价格 |
| qty | Double | 订单数量 |
| openPosition | Boolean | 开仓/平仓单，开仓为 true，平仓为false |
| cumQty | Double | 已成交数量 |
| avgPx | Double | 成交均价 |
| ordStatus | String | 订单状态，NEW - 新单；PARTIALLY\_FILLED - 部分成交；FILLED - 全部成交；CANCELED - 已撤；REJECTED - 已拒；WAITING - 等待（条件单） |
| iceberg | Boolean | 是否为冰山单 |
| showQty | Double | 冰山单显示数量 |
| source | String | 订单来源，Normal - 正常下单；Conditional - 条件单；StopWin - 止盈单；StopLoss - 止损单；TrailingStop - 追踪止损单；SysRisk - 爆仓单 |
| stopLossPrice | Double | 止损价 |
| stopWinPrice | Double | 止盈价 |
| stopWinType | Double | 止盈类型，Limit/Market |
| trailingStop | Double | 追踪止损 |
| triggerPrice | Double | 条件单触发价 |
| triggerType | String | 条件单触发价类型，LAST - 最后成交价，INDEX - 标记价 |

{% api-method method="post" host="https://api.hubi.com" path="/api/futures/enter\_order" %}
{% api-method-summary %}
下单
{% endapi-method-summary %}

{% api-method-description %}

{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-headers %}
{% api-method-parameter name="Content-Type" type="string" required=true %}
application/x-www-form-urlencoded
{% endapi-method-parameter %}
{% endapi-method-headers %}

{% api-method-query-parameters %}
{% api-method-parameter name="" type="string" required=false %}

{% endapi-method-parameter %}
{% endapi-method-query-parameters %}

{% api-method-form-data-parameters %}
{% api-method-parameter name="coin\_code" type="string" required=false %}
结算币种
{% endapi-method-parameter %}

{% api-method-parameter name="symbol" type="string" required=false %}
合约名字，eg:BTCUSD, BCHUSD..
{% endapi-method-parameter %}

{% api-method-parameter name="open\_position" type="boolean" required=false %}
开仓true, 平仓false
{% endapi-method-parameter %}

{% api-method-parameter name="quantity" type="integer" required=false %}
数量
{% endapi-method-parameter %}

{% api-method-parameter name="price" type="number" required=false %}
价格
{% endapi-method-parameter %}

{% api-method-parameter name="trade\_direction" type="string" required=false %}
方向，买卖BUY SELL
{% endapi-method-parameter %}

{% api-method-parameter name="order\_type" type="string" required=false %}
订单类型，限价LIMIT、市价MARKET
{% endapi-method-parameter %}

{% api-method-parameter name="stop\_loss\_price" type="string" required=false %}
不能和追踪止损（trailingStop）同时设置
{% endapi-method-parameter %}

{% api-method-parameter name="trailing\_stop" type="string" required=false %}
追踪止损，不能和止损价格（stopLossPrice）同时设置
{% endapi-method-parameter %}

{% api-method-parameter name="stop\_win\_price" type="number" required=false %}
止盈价格
{% endapi-method-parameter %}

{% api-method-parameter name="stop\_win\_type" type="string" required=false %}
限价 LIMIT，市价 MARKET
{% endapi-method-parameter %}

{% api-method-parameter name="trigger\_price" type="number" required=false %}
条件触发价
{% endapi-method-parameter %}

{% api-method-parameter name="trigger\_type" type="string" required=false %}
LAST 为最后成交价触发，INDEX 为标记价触发
{% endapi-method-parameter %}
{% endapi-method-form-data-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
{
	"status": 0,
	"result": "O101-20190910-033720-922-1879"
}
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| status | Integer | 状态，执行成功0，执行失败-1 |
| result | String | 订单号 |
| message | String | status -1 时返回错误消息 |

{% api-method method="post" host="https://api.hubi.com" path="/api/futures/cancel\_order" %}
{% api-method-summary %}
取消订单
{% endapi-method-summary %}

{% api-method-description %}

{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-headers %}
{% api-method-parameter name="Content-Type" type="string" required=false %}
application/x-www-form-urlencoded; charset=utf-8
{% endapi-method-parameter %}
{% endapi-method-headers %}

{% api-method-form-data-parameters %}
{% api-method-parameter name="order\_no" type="string" required=true %}
订单号
{% endapi-method-parameter %}
{% endapi-method-form-data-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
{
    "status": 0,
    "result": null
}
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| status | Integer | 状态，执行成功0，执行失败-1 |
| result | String | 无返回 |
| message | String | status -1 时返回错误消息 |

{% api-method method="post" host="https://api.hubi.com" path="/api/futures/cancel\_order\_batch" %}
{% api-method-summary %}
批量取消订单
{% endapi-method-summary %}

{% api-method-description %}

{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-form-data-parameters %}
{% api-method-parameter name="order\_nos" type="string" required=true %}
多个订单号，以英文逗号分隔
{% endapi-method-parameter %}
{% endapi-method-form-data-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```javascript
{
	"status": 0,
	"result": null
}
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| status | Integer | 状态，执行成功0，执行失败-1 |
| result | String | 无返回 |
| message | String | status -1 时返回错误消息 |

{% api-method method="post" host="https://api.hubi.com" path="/api/futures/close\_position" %}
{% api-method-summary %}
仓位全平
{% endapi-method-summary %}

{% api-method-description %}

{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-headers %}
{% api-method-parameter name="Content-Type" type="string" required=true %}
application/x-www-form-urlencoded; charset=utf-8
{% endapi-method-parameter %}
{% endapi-method-headers %}

{% api-method-form-data-parameters %}
{% api-method-parameter name="coin\_code" type="string" required=true %}
结算币种
{% endapi-method-parameter %}

{% api-method-parameter name="symbol" type="string" required=true %}
合约名字，BTCUSD, BCHUSD...
{% endapi-method-parameter %}

{% api-method-parameter name="position\_type" type="string" required=true %}
多仓 Long，空仓 Short
{% endapi-method-parameter %}
{% endapi-method-form-data-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
{
    "status": 0,
    "result": null
}
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| status | Integer | 状态，执行成功0，执行失败-1 |
| result | String | 无返回 |
| message | String | status -1 时返回错误消息 |

{% api-method method="post" host="https://api.hubi.com" path="/api/futures/switch\_to\_cross" %}
{% api-method-summary %}
切换为全仓模式
{% endapi-method-summary %}

{% api-method-description %}

{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-form-data-parameters %}
{% api-method-parameter name="coin\_code" type="string" required=true %}
结算币种
{% endapi-method-parameter %}
{% endapi-method-form-data-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
{
    "status": 0,
    "result": null
}
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| status | Integer | 状态，执行成功0，执行失败-1 |
| result | String | 无返回 |
| message | String | status -1 时返回错误消息 |

{% api-method method="post" host="https://api.hubi.com" path="/api/futures/change\_position\_leverage" %}
{% api-method-summary %}
切换单双向持仓模式
{% endapi-method-summary %}

{% api-method-description %}

{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-form-data-parameters %}
{% api-method-parameter name="coin\_code" type="string" required=true %}
结算币种
{% endapi-method-parameter %}

{% api-method-parameter name="two\_side\_position" type="boolean" required=true %}
双向持仓true, 单向持仓false
{% endapi-method-parameter %}
{% endapi-method-form-data-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
{
    "status": 0,
    "result": null
}
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| status | Integer | 状态，执行成功0，执行失败-1 |
| result | String | 无返回 |
| message | String | status -1 时返回错误消息 |

{% api-method method="post" host="https://api.hubi.com" path="/api/futures/risk\_setting" %}
{% api-method-summary %}
切换持仓杠杆
{% endapi-method-summary %}

{% api-method-description %}

{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-form-data-parameters %}
{% api-method-parameter name="coin\_code" type="string" required=true %}
结算币种
{% endapi-method-parameter %}

{% api-method-parameter name="symbol" type="string" required=true %}
合约名字，BTCUSD, BCHUSD...
{% endapi-method-parameter %}

{% api-method-parameter name="leverage" type="integer" required=true %}
杠杆倍数
{% endapi-method-parameter %}
{% endapi-method-form-data-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
{
    "status": 0,
    "result": null
}
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| status | Integer | 状态，执行成功0，执行失败-1 |
| result | String | 无返回 |
| message | String | status -1 时返回错误消息 |

{% api-method method="post" host="https://api.hubi.com" path="/api/futures/amend\_order" %}
{% api-method-summary %}
修改合约订单
{% endapi-method-summary %}

{% api-method-description %}

{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-form-data-parameters %}
{% api-method-parameter name="order\_no" type="string" required=false %}
订单编号
{% endapi-method-parameter %}

{% api-method-parameter name="quantity" type="integer" required=false %}
数量
{% endapi-method-parameter %}

{% api-method-parameter name="price" type="number" required=false %}
价格
{% endapi-method-parameter %}

{% api-method-parameter name="trigger\_price" type="number" required=false %}
条件单触发价
{% endapi-method-parameter %}

{% api-method-parameter name="stop\_loss\_price" type="number" required=false %}
止损价格
{% endapi-method-parameter %}

{% api-method-parameter name="trailing\_stop" type="number" required=false %}
追踪止损，不能和止损价格（stopLossPrice）同时设置
{% endapi-method-parameter %}

{% api-method-parameter name="stop\_win\_price" type="number" required=false %}
止盈价格
{% endapi-method-parameter %}

{% api-method-parameter name="stop\_win\_type" type="string" required=false %}
LIMIT为限价止盈，MARKET为市价止盈
{% endapi-method-parameter %}
{% endapi-method-form-data-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
{
    "status": 0,
    "result": null
}
```
{% endapi-method-response-example %}

{% api-method-response-example httpCode=500 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
{
    "status": -1,
    "message": "error_message"
}
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| status | Integer | 状态，执行成功0，执行失败-1 |
| result | String | 无返回 |
| message | String | status -1 时返回错误消息 |

## [返回错误编码](errors.md#jiao-yi-cuo-wu-bian-ma)

