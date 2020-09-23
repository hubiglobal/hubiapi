---
description: 期货合约相关的接口说明
---

# 合约

## 交易 REST API

{% page-ref page="sign.md" %}

{% hint style="info" %}
### 链接

国际：[https://api.hubi.com](https://api.hubi.com)

国内：[https://api.hubi.pub](https://api.hubi.pub)
{% endhint %}

### 获取增量订单列表\(返回上次请求到当前时间之间的订单\)

* 地址

```http
GET /api/futures/query_orders_pro
```

* 请求参数

| 字段 | 类型 | 说明 | 备注 |
| :--- | :--- | :--- | :--- |
| timestamp | Long | 时间戳 | 选填 |

* 响应

```javascript
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

* 返回**数组**元素说明

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

### 查询订单

* 地址

```http
GET /api/futures/query_order_by_id
```

* 请求参数

| 字段 | 类型 | 说明 | 备注 |
| :--- | :--- | :--- | :--- |
| order\_no | String | 订单号 | 必填 |

* 响应

```javascript
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

* 返回**对象**元素说明

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

### 查询合约资产

* 地址

```http
GET /api/futures/query_accounts
```

* 请求参数 无
* 响应

```javascript
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

* 返回**数组**元素说明

| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| currency | String | 币种 |
| cash | Double | 总资金 |
| withdrawableCash | Double | 可提资金 |
| frozenCash | Double | 冻结资金 |
| urPnl | Double | 账户浮动盈亏 |

### 查询仓位

* 地址

```http
GET /api/futures/query_position
```

* 请求参数 无
* 响应

```javascript
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

* 返回**数组**元素说明

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

### 查询未完成的订单

* 地址

```http
GET /api/futures/query_active_orders
```

* 请求参数

| 字段 | 类型 | 说明 | 备注 |
| :--- | :--- | :--- | :--- |
| symbol | String | 合约名字，BTCUSD, BCHUSD... | 选填 |

* 响应

```javascript
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

* 返回**数组**元素说明

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

### 下单\(开/平仓\)

* 地址

```http
POST /api/futures/enter_order

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

* 请求参数

| 字段 | 类型 | 说明 | 备注 |
| :--- | :--- | :--- | :--- |
| coin\_code | String | 结算币种 | 必填 |
| symbol | String | 合约名字，BTCUSD, BCHUSD... | 必填 |
| open\_position | String | 开仓true, 平仓false | 必填 |
| quantity | Double | 数量 | 必填 |
| price | Double | 价格 | 必填 |
| trade\_direction | String | 方向，买卖BUY SELL | 必填 |
| order\_type | String | 订单类型，限价LIMIT、市价MARKET | 必填 |
| stop\_loss\_price | String | 不能和追踪止损（trailingStop）同时设置 | 选填 |
| trailing\_stop | Double | 追踪止损，不能和止损价格（stopLossPrice）同时设置 | 选填 |
| stop\_win\_price | Double | 止盈价格 | 选填 |
| stop\_win\_type | String | 限价 LIMIT，市价 MARKET | 选填 |
| trigger\_price | Double | 条件触发价 | 选填 |
| trigger\_type | String | LAST 为最后成交价触发，INDEX 为标记价触发 | 选填 |
| tif\_type | String | GOOD\_TILL\_CANCEL: 一直有效至消失；IMMEDIATE\_OR\_CANCEL: 立即成交或取消；FILL\_OR\_KILL:完全成交或取消；QUEUE\_OR\_CANCEL: 被动委托 | 选填 |

* 响应

```javascript
{
    "status": 0,
    "result": "O101-20190910-033720-922-1879"
}
```

* 返回**对象**元素说明

| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| status | Integer | 状态，执行成功0，执行失败-1 |
| result | String | 订单号 |
| message | String | status -1 时返回错误消息 |

### 取消订单

* 地址

```http
POST /api/futures/cancel_order

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

* 请求参数

| 字段 | 类型 | 说明 | 备注 |
| :--- | :--- | :--- | :--- |
| order\_no | String | 订单号 | 必填 |

* 响应

```javascript
{
    "status": 0,
    "result": null
}
```

* 返回**对象**元素说明

| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| status | Integer | 状态，执行成功0，执行失败-1 |
| result | String | 无返回 |
| message | String | status -1 时返回错误消息 |

### 批量取消订单

* 地址

```http
POST /api/futures/cancel_order_batch

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

* 请求参数

| 字段 | 类型 | 说明 | 备注 |
| :--- | :--- | :--- | :--- |
| order\_nos | String | 多个订单号，以逗号分隔 | 必填 |

* 响应

```javascript
{
    "status": 0,
    "result": null
}
```

* 返回**对象**元素说明

| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| status | Integer | 状态，执行成功0，执行失败-1 |
| result | String | 无返回 |
| message | String | status -1 时返回错误消息 |

### 仓位全平

* 地址

```http
POST /api/futures/close_position

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

* 请求参数

| 字段 | 类型 | 说明 | 备注 |
| :--- | :--- | :--- | :--- |
| coin\_code | String | 结算币种 | 必填 |
| symbol | String | 合约名字，BTCUSD, BCHUSD... | 必填 |
| position\_type | String | 多仓 Long，空仓 Short | 必填 |

* 响应

```javascript
{
    "status": 0,
    "result": null
}
```

* 返回**对象**元素说明

| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| status | Integer | 状态，执行成功0，执行失败-1 |
| result | String | 无返回 |
| message | String | status -1 时返回错误消息 |

### 切换为全仓模式

* 地址

```http
POST /api/futures/switch_to_cross

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

* 请求参数

| 字段 | 类型 | 说明 | 备注 |
| :--- | :--- | :--- | :--- |
| coin\_code | String | 结算币种 | 必填 |

* 响应

```javascript
{
    "status": 0,
    "result": null
}
```

* 返回**对象**元素说明

| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| status | Integer | 状态，执行成功0，执行失败-1 |
| result | String | 无返回 |
| message | String | status -1 时返回错误消息 |

### 切换单双向持仓模式

* 地址

```http
POST /api/futures/switch_position_side

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

* 请求参数

| 字段 | 类型 | 说明 | 备注 |
| :--- | :--- | :--- | :--- |
| coin\_code | String | 结算币种 | 必填 |
| two\_side\_position | String | 双向持仓true, 单向持仓false | 必填 |

* 响应

```javascript
{
    "status": 0,
    "result": null
}
```

* 返回**对象**元素说明

| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| status | Integer | 状态，执行成功0，执行失败-1 |
| result | String | 无返回 |
| message | String | status -1 时返回错误消息 |

### 切换持仓杠杆

* 地址

```http
POST /api/futures/change_position_leverage

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

* 请求参数

| 字段 | 类型 | 说明 | 备注 |
| :--- | :--- | :--- | :--- |
| coin\_code | String | 结算币种 | 必填 |
| symbol | String | 合约名字，BTCUSD, BCHUSD... | 必填 |
| leverage | Integer | 杠杆倍数 | 必填 |

* 响应

```javascript
{
    "status": 0,
    "result": null
}
```

* 返回**对象**元素说明

| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| status | Integer | 状态，执行成功0，执行失败-1 |
| result | String | 无返回 |
| message | String | status -1 时返回错误消息 |

### 修改持仓

* 地址

```http
POST /api/futures/risk_setting

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

* 请求参数

| 字段 | 类型 | 说明 | 备注 |
| :--- | :--- | :--- | :--- |
| coin\_code | String | 结算币种 | 必填 |
| symbol | String | 合约名字，BTCUSD, BCHUSD... | 必填 |
| position\_type | String | LONG为多仓，SHORT为空仓 | 必填 |
| add\_deposit | Double | 追加/减少保证金，只对逐仓模式 | 可选 |
| stop\_loss\_price | Double | 止损价格 | 可选 |
| trailing\_stop | Double | 追踪止损，不能和止损价格（stopLossPrice）同时设置 | 可选 |
| stop\_win\_price | Double | 止盈价格 | 可选 |
| stop\_win\_type | String | LIMIT为限价止盈，MARKET为市价止盈 | 可选 |

* 响应

```javascript
{
    "status": 0,
    "result": null
}
```

* 返回**对象**元素说明

| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| status | Integer | 状态，执行成功0，执行失败-1 |
| result | String | 无返回 |
| message | String | status -1 时返回错误消息 |

### 修改合约订单

* 地址

```http
POST /api/futures/amend_order

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

* 请求参数

| 字段 | 类型 | 说明 | 备注 |
| :--- | :--- | :--- | :--- |
| order\_no | String | 订单编号 | 必填 |
| quantity | Double | 数量 | 可选 |
| price | Double | 价格 | 可选 |
| trigger\_price | Double | 条件单触发价 | 可选 |
| stop\_loss\_price | Double | 止损价格 | 可选 |
| trailing\_stop | Double | 追踪止损，不能和止损价格（stopLossPrice）同时设置 | 可选 |
| stop\_win\_price | Double | 止盈价格 | 可选 |
| stop\_win\_type | String | LIMIT为限价止盈，MARKET为市价止盈 | 可选 |

* 响应

```javascript
{
    "status": 0,
    "result": null
}
```

* 返回**对象**元素说明

| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| status | Integer | 状态，执行成功0，执行失败-1 |
| result | String | 无返回 |
| message | String | status -1 时返回错误消息 |

## 行情 REST API

{% hint style="info" %}
### 链接

国际：[https://api.hubi.com](https://api.hubi.com)

国内：[https://api.hubi.pub](https://api.hubi.pub)
{% endhint %}

### 获取币种

* 地址

```http
GET /api/futures/public/basic/refData
```

* 请求参数

  （无）

* 响应

```javascript
{
    "code": 0,
    "message": "OK",
    "result": [
        {
            "symbol": "XBTCUSD",
            "tick": 1.0,
            "lotSize": 1.0,
            "type": "PERP"
        },
        {
            "symbol": "XETHUSD",
            "tick": 0.05,
            "lotSize": 1.0,
            "type": "PERP"
        },
        {
            "symbol": "XEOSUSD",
            "tick": 0.001,
            "lotSize": 1.0,
            "type": "PERP"
        },
    ]
}
```

### 获取最新成交价

* 地址

```http
GET /api/futures/public/basic/lastPrice
```

* 请求参数

  （无）

* 响应

```javascript
{
  "code": 0,
  "message": "OK",
  "result": {
    "XBANDUSD": 13.825,
    "XBCHUSD": 259.25,
    "XETHUSD": 430.3,
    "XLINKUSD": 14.37,
    "XCOMPUSD": 223.1,
    "XEOSUSD": 3.02,
    "XLTCUSD": 56.93,
    "XXTZUSD": 2.993,
    "XDOTUSD": 6.015,
    "XBTCUSD": 11332.0
  }
}
```

### 获取市场深度

* 地址

```http
GET /api/futures/public/depth/depth
```

* 请求参数

| 字段 | 类型 | 说明 | 备注 |
| :--- | :--- | :--- | :--- |
| symbol | String | 合约名称，eg: XBTCUSD | 必填 |

* 响应

```javascript
{
  "code": 0,
  "message": "OK",
  "result": {
    "buyDepth": [
      {
        "price": 11333.0,
        "qty": 112772.0,
        "count": 10,
        "iceCount": 0
      },
      {
        "price": 11332.0,
        "qty": 89197.0,
        "count": 7,
        "iceCount": 0
      },
      {
        "price": 11331.0,
        "qty": 82113.0,
        "count": 6,
        "iceCount": 0
      }
    ],
    "trades": null
  }
}
```

### 获取最新成交记录

* 地址

```http
GET /api/futures/public/depth/trades
```

* 请求参数

| 字段 | 类型 | 说明 | 备注 |
| :--- | :--- | :--- | :--- |
| symbol | String | 合约名称，eg: XBTCUSD | 必填 |
| sequence | String | 上一次获取成交记录的最新一笔记录的 id；第一次请求传入空字符串 | 必填 |

* 响应

  返回最近50笔成交。

```javascript
{
  "code": 0,
  "message": "OK",
  "result": [
    {
      "id": "1599127941729000003",
      "symbol": "XBTCUSD",
      "price": 11335.0,
      "qty": 23608.0,
      "buyActive": false,
      "timestamp": "2020-09-03T10:12:21.730+0000"
    },
    {
      "id": "1599127940647000003",
      "symbol": "XBTCUSD",
      "price": 11335.0,
      "qty": 5220.0,
      "buyActive": true,
      "timestamp": "2020-09-03T10:12:20.648+0000"
    },
    {
      "id": "1599127938648000003",
      "symbol": "XBTCUSD",
      "price": 11336.0,
      "qty": 14509.0,
      "buyActive": false,
      "timestamp": "2020-09-03T10:12:18.649+0000"
    },
    {
      "id": "1599127933929000003",
      "symbol": "XBTCUSD",
      "price": 11337.0,
      "qty": 10422.0,
      "buyActive": true,
      "timestamp": "2020-09-03T10:12:13.930+0000"
    },
    {
      "id": "1599127931819000003",
      "symbol": "XBTCUSD",
      "price": 11335.0,
      "qty": 1000.0,
      "buyActive": false,
      "timestamp": "2020-09-03T10:12:11.820+0000"
    },
    {
      "id": "1599127930305000053",
      "symbol": "XBTCUSD",
      "price": 11336.0,
      "qty": 500.0,
      "buyActive": false,
      "timestamp": "2020-09-03T10:12:10.309+0000"
    }
  ]
}
```

### 通过下标索引获取历史 K 线

* 地址

```http
GET /api/futures/public/kLine/byIndex
```

* 请求参数

| 字段 | 类型 | 说明 | 备注 |
| :--- | :--- | :--- | :--- |
| symbol | String | 合约名称，eg: XBTCUSD | 必填 |
| type | String | K线类型,eg: 5M 15M 1H | 必填 |
| step | int | 返回的数据量，eg:10 | 必填 |
| from | Long | 起始时间戳 | 必填 |

* 响应

```javascript
{
  "code": 0,
  "message": "OK",
  "result": [
    {
      "source": null,
      "symbol": "XBTCUSD",
      "open": 10672.0,
      "close": 10654.0,
      "high": 10674.0,
      "low": 10651.0,
      "keyTime": "2020-09-15T08:40:00.000+0000",
      "timeStamp": "2020-09-15T08:40:29.374+0000",
      "volume": 1905671.0,
      "turnover": 178.73180788365713
    },
    {
      "source": null,
      "symbol": "XBTCUSD",
      "open": 10710.0,
      "close": 10672.0,
      "high": 10720.0,
      "low": 10668.0,
      "keyTime": "2020-09-15T08:35:00.000+0000",
      "timeStamp": "2020-09-15T08:39:59.971+0000",
      "volume": 2760924.0,
      "turnover": 258.3779874651953
    },
    {
      "source": null,
      "symbol": "XBTCUSD",
      "open": 10723.0,
      "close": 10712.0,
      "high": 10723.0,
      "low": 10710.0,
      "keyTime": "2020-09-15T08:30:00.000+0000",
      "timeStamp": "2020-09-15T08:34:59.971+0000",
      "volume": 602240.0,
      "turnover": 56.18765650652743
    },
    {
      "source": null,
      "symbol": "XBTCUSD",
      "open": 10724.0,
      "close": 10725.0,
      "high": 10729.0,
      "low": 10721.0,
      "keyTime": "2020-09-15T08:25:00.000+0000",
      "timeStamp": "2020-09-15T08:29:58.190+0000",
      "volume": 813461.0,
      "turnover": 75.84876072793301
    }
  ]
}
```

### 通过时间索引获取历史 K 线

* 地址

```http
GET /api/futures/public/kLine/byTime
```

* 请求参数

| 字段 | 类型 | 说明 | 备注 |
| :--- | :--- | :--- | :--- |
| symbol | String | 合约名称，eg: XBTCUSD | 必填 |
| type | String | K线类型,eg: 5M 15M 1H | 必填 |
| step | int | 返回的数据量，eg:10 | 必填 |
| from | Long | 起始时间戳 | 可选 |

* 响应

```javascript
{
  "code": 0,
  "message": "OK",
  "result": [
    {
      "source": null,
      "symbol": "XBTCUSD",
      "open": 10672.0,
      "close": 10638.0,
      "high": 10674.0,
      "low": 10613.0,
      "keyTime": "2020-09-15T08:40:00.000+0000",
      "timeStamp": "2020-09-15T08:42:34.654+0000",
      "volume": 8093755.0,
      "turnover": 759.9430209689488
    },
    {
      "source": null,
      "symbol": "XBTCUSD",
      "open": 10710.0,
      "close": 10672.0,
      "high": 10720.0,
      "low": 10668.0,
      "keyTime": "2020-09-15T08:35:00.000+0000",
      "timeStamp": "2020-09-15T08:39:59.971+0000",
      "volume": 2760924.0,
      "turnover": 258.3779874651953
    },
    {
      "source": null,
      "symbol": "XBTCUSD",
      "open": 10723.0,
      "close": 10712.0,
      "high": 10723.0,
      "low": 10710.0,
      "keyTime": "2020-09-15T08:30:00.000+0000",
      "timeStamp": "2020-09-15T08:34:59.971+0000",
      "volume": 602240.0,
      "turnover": 56.18765650652743
    }
  ]
}
```

### 获取最新一根 K 线数据

* 地址

```http
GET /api/futures/public/kLine/latest
```

* 请求参数

| 字段 | 类型 | 说明 | 备注 |
| :--- | :--- | :--- | :--- |
| symbol | String | 合约名称，eg: XBTCUSD | 必填 |
| type | String | K线类型,eg: 5M 15M 1H | 必填 |

* 响应

```javascript
{
  "code": 0,
  "message": "OK",
  "result": {
    "source": null,
    "symbol": "XBTCUSD",
    "open": 11331.0,
    "close": 11336.0,
    "high": 11337.0,
    "low": 11330.0,
    "keyTime": "2020-09-03T10:15:00.000+0000",
    "timeStamp": "2020-09-03T10:16:21.973+0000",
    "volume": 319767.0,
    "turnover": 28.212856174373286
  }
}
```

### 获取资金费率

* 地址

```http
GET /api/futures/public/kLine/fundingRate
```

* 请求参数

| 字段 | 类型 | 说明 | 备注 |
| :--- | :--- | :--- | :--- |
| symbols | String | 合约名称，多个合约以逗号分隔，eg: XBTCUSD,XETHUSD | 必填 |

* 响应

```javascript
{
  "code": 0,
  "message": "OK",
  "result": {
    "source": null,
    "symbol": "XBTCUSD",
    "open": 11331.0,
    "close": 11336.0,
    "high": 11337.0,
    "low": 11330.0,
    "keyTime": "2020-09-03T10:15:00.000+0000",
    "timeStamp": "2020-09-03T10:16:21.973+0000",
    "volume": 319767.0,
    "turnover": 28.212856174373286
  }
}
```

### 获取最近24小时的成交统计数据

* 地址

```http
GET /api/futures/public/kLine/tradeStatistics
```

* 请求参数

| 字段 | 类型 | 说明 | 备注 |
| :--- | :--- | :--- | :--- |
| symbols | String | 合约名称，多个合约以逗号分隔，eg: XBTCUSD,XETHUSD | 必填 |

* 响应

```javascript
{
  "code": 0,
  "message": "OK",
  "result": [
    {
      "symbol": "XBTCUSD",
      "maxPrice": 11723.0,
      "minPrice": 11179.0,
      "priceChange": -399.0,
      "priceChangeRatio": -0.03404146403890453,
      "volume": 1.184787737E9,
      "turnover": 104064.50379496589,
      "lastPrice": 11322.0,
      "volumeRatioList": null
    },
    {
      "symbol": "XETHUSD",
      "maxPrice": 468.75,
      "minPrice": 421.45,
      "priceChange": -38.75,
      "priceChangeRatio": -0.08266666666666667,
      "volume": 4.95616496E8,
      "turnover": 1128724.240075755,
      "lastPrice": 430.0,
      "volumeRatioList": null
    }
  ]
}
```

### 获取系统合约持仓量。

* 地址

```http
GET /api/futures/public/kLine/openInterest
```

* 请求参数

| 字段 | 类型 | 说明 | 备注 |
| :--- | :--- | :--- | :--- |
| symbol | String | 合约名称，eg: BTCUSD （注意：和合约名称进行区分，标记名前去掉X） | 必填 |

* 响应

```javascript
{
  "code": 0,
  "message": "OK",
  "result": {
    "symbol": "BTCUSD",
    "value": 31865.068938608492,
    "date": "2020-09-03T10:21:27.659+0000",
    "qty": 3.60361268E8
  }
}
```

## 行情 WebSocket API

{% hint style="info" %}
### 链接

国际：_**wss://api.hubi.com/ws/futures/public/market**_

国内：_**wss://api.hubi.pub/ws/futures/public/market**_
{% endhint %}

### 指令格式

#### 订阅数据更新

订阅数据的动态更新，需要指定具体的 channel，以及需要的参数

```javascript
{"op":"subscribe", "channel":"xxxxxx", ...}
```

#### 数据更新

根据订阅返回更新数据，event 为对应订阅的 channel 路径

```javascript
{"event": "xxxxxx", ...}
```

#### 取消订阅

取消订阅数据的动态更新，与订阅类似，同样需要指定具体的 channel，以及需要的参数

```javascript
{"op":"unsubscribe", "channel":"xxxxxx", ...}
```

### 标记价

订阅格式如下：

```javascript
{"op":"subscribe", "channel":"/api/index/price", "key":"BTCUSD"}
```

数据更新格式如下：

```javascript
{
    "key": "BTCUSD", 
    "event": "/api/index/price", 
    "value": 9482.89925, 
    "updatedTime": "Jun 17, 2020 09:13:43 AM"
}
```

取消订阅：

```javascript
{"op":"unsubscribe", "channel":"/api/index/price", "key":"BTCUSD"}
```

### 深度

订阅格式如下：

```javascript
{"op":"subscribe", "channel":"/api/depth/depth", "key":"XBTCUSD"}
```

数据更新格式如下：

```javascript
{
    "buyDepth": [
        {
            "price": 9482, 
            "qty": 160929, 
            "count": 13, 
            "iceCount": 0
        }, 
        {
            "price": 9481, 
            "qty": 130095, 
            "count": 7, 
            "iceCount": 0
        }, 
        {
            "price": 9463, 
            "qty": 384114, 
            "count": 6, 
            "iceCount": 0
        }, 
        {
            "price": 9483, 
            "qty": 0, 
            "count": 0, 
            "iceCount": 0
        }
    ], 
    "sellDepth": [
        {
            "price": 9483, 
            "qty": 9331, 
            "count": 1, 
            "iceCount": 0
        }, 
        {
            "price": 9494, 
            "qty": 201324, 
            "count": 13, 
            "iceCount": 0
        }, 
        {
            "price": 9503, 
            "qty": 0, 
            "count": 0, 
            "iceCount": 0
        }
    ], 
    "trades": [
        {
            "id": "1592385671049000011", 
            "symbol": "XBTCUSD", 
            "price": 9482, 
            "qty": 704, 
            "buyActive": false, 
            "timestamp": "Jun 17, 2020 09:21:11 AM"
        }, 
        {
            "id": "1592385671049000008", 
            "symbol": "XBTCUSD", 
            "price": 9482, 
            "qty": 243, 
            "buyActive": false, 
            "timestamp": "Jun 17, 2020 09:21:11 AM"
        }, 
        {
            "id": "1592385671049000005", 
            "symbol": "XBTCUSD", 
            "price": 9482, 
            "qty": 866, 
            "buyActive": false, 
            "timestamp": "Jun 17, 2020 09:21:11 AM"
        }, 
        {
            "id": "1592385671048000003", 
            "symbol": "XBTCUSD", 
            "price": 9483, 
            "qty": 11596, 
            "buyActive": true, 
            "timestamp": "Jun 17, 2020 09:21:11 AM"
        }
    ], 
    "key": "XBTCUSD", 
    "event": "/api/depth/depth"
}
```

取消订阅：

```javascript
{"op":"unsubscribe", "channel":"/api/depth/depth", "key":"XBTCUSD"}
```

### 资金费率

订阅格式如下：

```javascript
{"op":"subscribe", "channel":"/api/kLine/fundingRate", "key":"XBTCUSD"}
```

数据更新格式如下：

```javascript
{
    "key": "XBTCUSD", 
    "event": "/api/kLine/fundingRate", 
    "rate": -0.0001, 
    "date": "Jun 17, 2020 12:00:00 PM"
}
```

取消订阅：

```javascript
{"op":"unsubscribe", "channel":"/api/kLine/fundingRate", "key":"XBTCUSD"}
```

### 持仓量

订阅格式如下：

```javascript
{"op":"subscribe", "channel":"/api/kLine/openInterest", "key":"BTCUSD"}
```

数据更新格式如下：

```javascript
{
    "key": "BTCUSD", 
    "event": "/api/kLine/openInterest", 
    "value": 6323.8854145971545, 
    "date": "Jun 17, 2020 09:42:26 AM", 
    "qty": 60000677
}
```

取消订阅：

```javascript
{"op":"unsubscribe", "channel":"/api/kLine/openInterest", "key":"BTCUSD"}
```

### 24小时成交统计

订阅格式如下：

```javascript
{"op":"subscribe", "channel":"/api/kLine/tradeStatistics", "key":"XBTCUSD"}
```

数据更新格式如下：

```javascript
{
    "key": "XBTCUSD", 
    "event": "/api/kLine/tradeStatistics", 
    "maxPrice": 9591, 
    "minPrice": 9396, 
    "priceChange": -9, 
    "priceChangeRatio": -0.0009483667017913594, 
    "volume": 476875741, 
    "turnover": 50260.22409205384, 
    "lastPrice": 9481, 
    "volumeRatioList": [
        0.04125656269781181, 
        0.3933914651530134, 
        0.5241644199874432, 
        0.5129134007442344, 
        0.5047321924171744, 
        0.5168734503777649, 
        0.4990268608778209
    ]
}
```

取消订阅：

```javascript
{"op":"unsubscribe", "channel":"/api/kLine/tradeStatistics", "key":"XBTCUSD"}
```

### K 线更新

订阅格式如下：

```javascript
{"op":"subscribe", "channel":"/api/kLine/kLine", "key":"XBTCUSD", "type":"1M"}
```

数据更新格式如下：

```javascript
{
    "event": "/api/kLine/kLine", 
    "key": "XBTCUSD", 
    "type": "1M", 
    "open": 9482, 
    "close": 9481, 
    "high": 9482, 
    "low": 9478, 
    "keyTime": "Jun 17, 2020 09:47:00 AM", 
    "timeStamp": "Jun 17, 2020 09:47:52 AM", 
    "volume": 102120, 
    "turnover": 10.771662545934651
}
```

取消订阅：

```javascript
{"op":"unsubscribe", "channel":"/api/kLine/kLine", "key":"XBTCUSD", "type":"1M"}
```

## 错误代码列表

### 通用错误消息

| 错误消息 | 描述 |
| :--- | :--- |
| ILLEGAL\_NONCE | X-API-Nonce 值不合法错误 |
| ILLEGAL\_VERSION | 版本不兼容 |
| ILLEGAL\_TIMESTAMP | 时间戳错误 |
| ILLEGAL\_SIGN | 签名错误 |
| ILLEGAL\_ARGUMENT | 参数错误 |
| ILLEGAL\_SIGN\_TYPE | 签名类型不正确 |
| ILLEGAL\_CHARSET | 字符集不合法 |
| ILLEGAL\_ACCESS\_KEY | 访问密钥不正确 |
| ILLEGAL\_SCOPE | 访问范围不合法的，（只读或者读写） |
| ILLEGAL\_CLIENT\_IP | IP 地址被限制 |

### 交易错误码

| 错误编码 | 描述 |
| :--- | :--- |
| 0 | 正常返回 |
| 25004 | 订单已存在 |
| 25005 | 订单未发现 |
| 25006 | 订单已完成 |
| 25007 | 账户不允许此操作 |
| 25008 | 每日订单超出限制 |
| 25009 | 订单数量无效 |
| 25010 | 数据转换错误 |
| 25011 | 订单数量太小 |
| 25012 | 订单价格必须大于0 |
| 25013 | 不能修改订单数量 |
| 25014 | 订单的价格或数量必须修改 |
| 25015 | 订单无法与账户绑定 |
| 25016 | 无效的下单类型 |
| 25017 | 无效的买卖单类型 |
| 25018 | 交易所订单号没有 |
| 25019 | 计算资金的价格无效 |
| 25020 | 无法确定有效的报价 |
| 25021 | 资金不足 |
| 25022 | 交易标的有订单尚未确定 |
| 25023 | 只能把订单数量减少 |
| 25024 | 订单剩余数量不足以减少 |
| 25025 | 可平仓数量不足 |
| 25026 | 风控价格已被当前价格触发 |
| 25027 | 触发价格不合理 |
| 25028 | 无双向仓位可合并 |
| 25030 | 没有修改任何值 |
| 25031 | 杠杆值有误 |
| 25032 | 止损价有误 |
| 25033 | 止盈价有误 |
| 25034 | 全仓模式不支持追加保证金 |
| 25035 | 仓位不存在 |
| 25036 | 保证金不足 |
| 25043 | 买单价格不能低于止损价 |
| 25044 | 买单价格不能高于止盈价 |
| 25045 | 卖单价格不能高于止损价 |
| 25046 | 卖单价格不能低于止盈价 |
| 25049 | 风险限额设置不足 |
| 25050 | 标志价格还没有确定 |
| 25051 | 委托价格偏离标记价格过多 |

