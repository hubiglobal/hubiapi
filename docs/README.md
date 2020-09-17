# 合约 API

现货

##  接入准备

您需要先登录[网页端](https://www.hubi.pub/zh/api/setting)申请API key，必要的话须设置访问白名单权限，默认不限制。

创建成功后请务必记住以下信息：

`API Key` API访问唯一标识

`Secret Key`  签名认证加密所使用的密钥（仅申请时可见）

`Access Token` 用户身份标识（仅申请时可见）

## 接口鉴权

Hubi提供的接口包括公共接口和私有接口两种类型。

公共接口可用于获取行情数据，无需认证即可调用。

私有接口可用于获取基础数据、交易管理，每个私有请求必须使用 API Key进行签名。

## 接入URLs

| 类型      | URL                                          | 说明         |
| --------- | -------------------------------------------- | ------------ |
| REST      | https://api.hubi.com 或 https://api.hubi.pub | 国内使用.pub |
| WebSocket | wss://api.hubi.com 或 wss://api.hubi.pub     | 国内使用.pub |

## 签名

签名说明

REST API 请求在通过 internet 传输的过程中极有可能被篡改，为了确保请求未被更改，除公共接口（行情数据）外的私有接口均必须使用您的 API Key 做签名认证，以校验参数或参数值在传输途中是否发生了更改。

一个合法的请求头部需包含以下信息：

- `X-API-Version`： 请求的API版本，现在只支持1.0.0。

- `X-API-Key`： Access Key，您申请的 API Key中的Access Key。

- `X-API-Timestamp`： 请求时间戳，ISO格式，如：2018-07-18T01:25:47.048Z，字符串。

- `X-API-Nonce`：通过md5(client_id + timestamp + seqNum)计算行到，client_id是申请的Access Key， timestamp是请求时间戳，seqNum是一串随机数。

- `X-API-Signature-Params`：需要签名的参数，使用逗号分隔，如 top,coin_code,price_coin_code

- `X-API-Signature`： 签名结果，通过HmacSHA256(client_secret, params + version + nonce + url) 得到，client_secret是您申请的API Key中的Secret Key，params是参与签名的参数键值对，通过"键=值&"来连接，如 top=100&coin_code=HUB&price_coin_code=USDT，按照`X-API-Signature-Params`指定的参数顺序排序，version=1.0.0，字段连接时不需要加号（+）详见示例。

- `Authorization`： <font color=#8E25B8>Bearer</font> access_token， Bearer 固定的，access_token是您申请的API Key中的 access token。

<font color="#F70000">**GET, PUT, POST请求的参数都需要参与签名运算**</font>

### 示例

*（注意相同颜色的文本之间的关系）*

`URL`：POST /api/entrust/current/top

`参数`：top=100,coin_code=HUB,price_coin_code=USDT

`Access Key` = <font color=#AD18C7>14e5aa14f20345cbaf020e9b8562cbd6</font>

`Secret Key` = <font color="#8C192C">b3a0a2a36d0f4b52b697ac2df3484bc2</font>

`Access Token` = <font color="#0BA118">b868dd65-84da-43f5-b2ca-d3dcc843e795</font>

**步骤**

1. 设置随机数 seqNum=999 （建议用自增序列，每请求一次增加1）
2. 获取当前时间得到 timestamp=2019-12-30T15:52:41.788
3. 计算MD5(<font color=#AD18C7>14e5aa14f20345cbaf020e9b8562cbd6</font><font color="#198C25">2019-12-30T15:52:41.788</font><font color="#D6042B">999</font>)，得到nonce = 3c72aa1b1d0b486b4bcd9350e9410ad5
4. HmacSHA256签名，HmacSHA256(<font color="#8C192C">b3a0a2a36d0f4b52b697ac2df3484bc2</font>, <font color="#0F0778">top=100&coin_code=HUB&price_coin_code=USDT</font><font color="#FC1D00">1.0.0</font><font color="#D6A504">3c72aa1b1d0b486b4bcd9350e9410ad5</font><font color="#8604D6">/api/entrust/current/top</font>)，得到signature = ab8c4d4535cf8d33283462d6c8571b8ca4241b608fc77659a1be2d6dae9709b2

*md5, sha256 加密时字符集使用 UTF-8*

最后得到 header

- `X-API-Version`： 1.0.0
- `X-API-Key`： <font color=#AD18C7>14e5aa14f20345cbaf020e9b8562cbd6</font>
- `X-API-Timestamp`： 2019-12-30T15:52:41.788
- `X-API-Nonce`：3c72aa1b1d0b486b4bcd9350e9410ad5
- `X-API-Signature-Params`：top,coin_code,price_coin_code
- `X-API-Signature`： ab8c4d4535cf8d33283462d6c8571b8ca4241b608fc77659a1be2d6dae9709b2
- `Authorization`：Bearer <font color="#0BA118">b868dd65-84da-43f5-b2ca-d3dcc843e795</font>



## 交易 REST API

URL: https://api.hubi.com 或 https://api.hubi.pub

### API 列表

| 接口                                                         | 说明                               |
| ------------------------------------------------------------ | ---------------------------------- |
| [`GET /api/futures/query_orders_pro`](#query_orders_pro) | 获取用户的增量订单列表（返回上次请求到当前时间之间的订单） |
| [`GET /api/futures/query_order_by_id`](#query_order_by_id) | 查询当前用户的某个合约订单 |
| [`GET /api/futures/query_accounts`](#query_accounts) | 查询当前用户的合约资产 |
| [`GET /api/futures/query_position`](#query_position) | 查询当前用户的合约仓位 |
| [`GET /api/futures/query_active_orders`](#query_active_orders) | 查询当前用户未完成的合约订单 |
| [`POST /api/futures/enter_order`](#enter_order) | 当前用户合约开平仓 |
| [`POST /api/futures/cancel_order`](#cancel_order) | 取消当前用户的合约订单 |
| [`POST /api/futures/cancel_order_batch`](#cancel_order_batch) | 批量取消当前用户的合约订单 |
| [`POST /api/futures/close_position`](#close_position) | 当前用户仓位全平 |
| [`POST /api/futures/switch_to_cross`](#switch_to_cross) | 当前用户切换为全仓模式 |
| [`POST /api/futures/switch_position_side`](#switch_position_side) | 当前用户切换单双向持仓模式 |
| [`POST /api/futures/change_position_leverage`](#change_position_leverage) | 切换当前用户的持仓杠杆 |
| [`POST /api/futures/risk_setting`](#risk_setting) | 修改当前用户的合约持仓 |
| [`POST /api/futures/amend_order`](#amend_order) | 修改当前用户的合约订单 |



### <span id="query_orders_pro">获取用户的合约增量订单列表(返回上次请求到当前时间之间的订单)</span>

- 地址

```http
GET /api/futures/query_orders_pro
```

- 请求参数

| 字段   | 类型   | 说明         | 备注                         |
| ------ | ------ | ------------ | ---------------------------- |
| timestamp | Long | 时间戳 | 选填 |

- 响应

```json
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

- 返回<font color="#F70000">**数组**</font>元素说明

| 字段   | 类型   | 说明              |
| ------ | ------ | ----------------- |
| id  | String  | 订单id，eg: O101-20190910-033720-922-1879  |
| currency  | String  | 交易币种  |
| symbol  | String  | 合约名称  |
| created  | Date  | 订单创建时间  |
| modified  | Date  | 订单发生变动时间  |
| side  | String  | 订单方向，Buy/Sell  |
| type  | String  | 订单类型，Limit/Market  |
| price  | Double  | 订单价格  |
| qty  | Double  | 订单数量  |
| openPosition  | Boolean  | 开仓/平仓单，开仓为 true，平仓为false  |
| cumQty  | Double  | 已成交数量  |
| avgPx  | Double  | 成交均价  |
| ordStatus  | String  | 订单状态，NEW - 新单；PARTIALLY_FILLED - 部分成交；FILLED - 全部成交；CANCELED - 已撤；REJECTED - 已拒；WAITING - 等待（条件单）  |
| iceberg  | Boolean  | 是否为冰山单  |
| showQty  | Double  | 冰山单显示数量  |
| source  | String  | 订单来源，Normal - 正常下单；Conditional - 条件单；StopWin - 止盈单；StopLoss - 止损单；TrailingStop - 追踪止损单；SysRisk - 爆仓单  |
| stopLossPrice  | Double  | 止损价  |
| stopWinPrice  | Double  | 止盈价  |
| stopWinType  | Double  | 止盈类型，Limit/Market  |
| trailingStop  | Double  | 追踪止损  |
| triggerPrice  | Double  | 条件单触发价  |
| triggerType  | String  | 条件单触发价类型，LAST - 最后成交价，INDEX - 标记价  |



### <span id="query_order_by_id">查询当前用户的某个合约订单</span>

- 地址

```http
GET /api/futures/query_order_by_id
```

- 请求参数

| 字段   | 类型   | 说明         | 备注                         |
| ------ | ------ | ------------ | ---------------------------- |
| order_no | String | 订单号 | 必填 |

- 响应

```json
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

- 返回<font color="#F70000">**对象**</font>元素说明

| 字段   | 类型   | 说明              |
| ------ | ------ | ----------------- |
| id  | String  | 订单id，eg: O101-20190910-033720-922-1879  |
| currency  | String  | 交易币种  |
| symbol  | String  | 合约名称  |
| created  | Date  | 订单创建时间  |
| modified  | Date  | 订单发生变动时间  |
| side  | String  | 订单方向，Buy/Sell  |
| type  | String  | 订单类型，Limit/Market  |
| price  | Double  | 订单价格  |
| qty  | Double  | 订单数量  |
| openPosition  | Boolean  | 开仓/平仓单，开仓为 true，平仓为false  |
| cumQty  | Double  | 已成交数量  |
| avgPx  | Double  | 成交均价  |
| ordStatus  | String  | 订单状态，NEW - 新单；PARTIALLY_FILLED - 部分成交；FILLED - 全部成交；CANCELED - 已撤；REJECTED - 已拒；WAITING - 等待（条件单）  |
| iceberg  | Boolean  | 是否为冰山单  |
| showQty  | Double  | 冰山单显示数量  |
| source  | String  | 订单来源，Normal - 正常下单；Conditional - 条件单；StopWin - 止盈单；StopLoss - 止损单；TrailingStop - 追踪止损单；SysRisk - 爆仓单  |
| stopLossPrice  | Double  | 止损价  |
| stopWinPrice  | Double  | 止盈价  |
| stopWinType  | Double  | 止盈类型，Limit/Market  |
| trailingStop  | Double  | 追踪止损  |
| triggerPrice  | Double  | 条件单触发价  |
| triggerType  | String  | 条件单触发价类型，LAST - 最后成交价，INDEX - 标记价  |



### <span id="query_accounts">查询当前用户的所有合约资产</span>

- 地址

```http
GET /api/futures/query_accounts
```

- 请求参数
  无

- 响应

```json
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

- 返回<font color="#F70000">**数组**</font>元素说明

| 字段   | 类型   | 说明              |
| ------ | ------ | ----------------- |
| currency  | String  | 币种  |
| cash  | Double  | 总资金  |
| withdrawableCash  | Double  | 可提资金  |
| frozenCash  | Double  | 冻结资金  |
| urPnl  | Double  | 账户浮动盈亏   |



### <span id="query_position">查询当前用户的合约仓位</span>

- 地址

```http
GET /api/futures/query_position
```

- 请求参数
  无

- 响应

```json
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

- 返回<font color="#F70000">**数组**</font>元素说明

| 字段   | 类型   | 说明              |
| ------ | ------ | ----------------- |
| currency  | String  | 交易币种  |
| symbol  | String  | 合约名称  |
| side  | String  | 持仓方向，Long - 多仓，Short - 空仓  |
| qty  | Double  | 持仓数量  |
| individualPosition  | Boolean  | 是否逐仓，true - 逐仓，false - 全仓  |
| price  | Double  | 持仓价格  |
| closableQty  | Double  | 可平数量  |
| pnlRate  | Double  | 浮动盈亏比例  |
| value  | Double  | 持仓价值  |
| positionLeverage  | Double  | 杠杆  |
| stopLossPrice  | Double  | 止损价设定  |
| stopWinPrice  | Double  | 止盈价设定  |
| stopWinType  | String  | 止盈类型，Limit/Market  |
| trailingStop  | Double  | 追踪止损价距  |
| trailingStopPrice  | Double  | 根据追踪止损算出的止损价  |
| pnl  | Double  | 已实现盈亏  |
| urPnL  | Double  | 浮动盈亏  |
| liquidationPrice  | Double  | 爆仓价  |
| deposit  | Double  | 保证金  |



### <span id="query_active_orders">查询当前用户未完成的合约订单</span>

- 地址

```http
GET /api/futures/query_active_orders
```

- 请求参数

| 字段   | 类型   | 说明         | 备注                         |
| ------ | ------ | ------------ | ---------------------------- |
| symbol | String | 合约名字，BTCUSD, BCHUSD... | 选填 |

- 响应

```json
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

- 返回<font color="#F70000">**数组**</font>元素说明

| 字段   | 类型   | 说明              |
| ------ | ------ | ----------------- |
| id  | String  | 订单id，eg: O101-20190910-033720-922-1879  |
| currency  | String  | 交易币种  |
| symbol  | String  | 合约名称  |
| created  | Date  | 订单创建时间  |
| modified  | Date  | 订单发生变动时间  |
| side  | String  | 订单方向，Buy/Sell  |
| type  | String  | 订单类型，Limit/Market  |
| price  | Double  | 订单价格  |
| qty  | Double  | 订单数量  |
| openPosition  | Boolean  | 开仓/平仓单，开仓为 true，平仓为false  |
| cumQty  | Double  | 已成交数量  |
| avgPx  | Double  | 成交均价  |
| ordStatus  | String  | 订单状态，NEW - 新单；PARTIALLY_FILLED - 部分成交；FILLED - 全部成交；CANCELED - 已撤；REJECTED - 已拒；WAITING - 等待（条件单）  |
| iceberg  | Boolean  | 是否为冰山单  |
| showQty  | Double  | 冰山单显示数量  |
| source  | String  | 订单来源，Normal - 正常下单；Conditional - 条件单；StopWin - 止盈单；StopLoss - 止损单；TrailingStop - 追踪止损单；SysRisk - 爆仓单  |
| stopLossPrice  | Double  | 止损价  |
| stopWinPrice  | Double  | 止盈价  |
| stopWinType  | Double  | 止盈类型，Limit/Market  |
| trailingStop  | Double  | 追踪止损  |
| triggerPrice  | Double  | 条件单触发价  |
| triggerType  | String  | 条件单触发价类型，LAST - 最后成交价，INDEX - 标记价  |



### <span id="enter_order">当前用户合约开平仓</span>

- 地址

```http
POST /api/futures/enter_order

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

- 请求参数

| 字段   | 类型   | 说明         | 备注                         |
| ------ | ------ | ------------ | ---------------------------- |
| coin_code | String | 结算币种 | 必填 |
| symbol | String | 合约名字，BTCUSD, BCHUSD... | 必填 |
| open_position | String | 开仓true, 平仓false | 必填 |
| quantity | Double | 数量 | 必填 |
| price | Double | 价格 | 必填 |
| trade_direction | String | 方向，买卖BUY SELL | 必填 |
| order_type | String | 订单类型，限价LIMIT、市价MARKET | 必填 |
| stop_loss_price | String | 不能和追踪止损（trailingStop）同时设置 | 选填 |
| trailing_stop | Double | 追踪止损，不能和止损价格（stopLossPrice）同时设置 | 选填 |
| stop_win_price | Double | 止盈价格 | 选填 |
| stop_win_type | String | 限价 LIMIT，市价 MARKET | 选填 |
| trigger_price | Double | 条件触发价 | 选填 |
| trigger_type | String | LAST 为最后成交价触发，INDEX 为标记价触发 | 选填 |
| tif_type | String | GOOD_TILL_CANCEL: 一直有效至消失；IMMEDIATE_OR_CANCEL: 立即成交或取消；FILL_OR_KILL:完全成交或取消；QUEUE_OR_CANCEL: 被动委托 | 选填 |

- 响应

```json
{
	"status": 0,
	"result": "O101-20190910-033720-922-1879"
}

```

- 返回<font color="#F70000">**对象**</font>元素说明

| 字段   | 类型   | 说明              |
| ------ | ------ | ----------------- |
| status  | Integer  | 状态，执行成功0，执行失败-1  |
| result  | String  | 订单号  |
| message  | String  | status -1 时返回错误消息  |



### <span id="cancel_order">取消当前用户的合约订单</span>

- 地址

```http
POST /api/futures/cancel_order

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

- 请求参数

| 字段   | 类型   | 说明         | 备注                         |
| ------ | ------ | ------------ | ---------------------------- |
| order_no | String | 订单号 | 必填 |

- 响应

```json
{
	"status": 0,
	"result": null
}

```

- 返回<font color="#F70000">**对象**</font>元素说明

| 字段   | 类型   | 说明              |
| ------ | ------ | ----------------- |
| status  | Integer  | 状态，执行成功0，执行失败-1  |
| result  | String  | 无返回  |
| message  | String  | status -1 时返回错误消息  |



### <span id="cancel_order_batch">批量取消当前用户的合约订单</span>

- 地址

```http
POST /api/futures/cancel_order_batch

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

- 请求参数

| 字段   | 类型   | 说明         | 备注                         |
| ------ | ------ | ------------ | ---------------------------- |
| order_nos | String | 多个订单号，以逗号分隔 | 必填 |

- 响应

```json
{
	"status": 0,
	"result": null
}

```

- 返回<font color="#F70000">**对象**</font>元素说明

| 字段   | 类型   | 说明              |
| ------ | ------ | ----------------- |
| status  | Integer  | 状态，执行成功0，执行失败-1  |
| result  | String  | 无返回  |
| message  | String  | status -1 时返回错误消息  |



### <span id="close_position">当前用户合约仓位全平</span>

- 地址

```http
POST /api/futures/close_position

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

- 请求参数

| 字段   | 类型   | 说明         | 备注                         |
| ------ | ------ | ------------ | ---------------------------- |
| coin_code | String | 结算币种 | 必填 |
| symbol | String | 合约名字，BTCUSD, BCHUSD... | 必填 |
| position_type | String | 多仓 Long，空仓 Short | 必填 |

- 响应

```json
{
	"status": 0,
	"result": null
}

```

- 返回<font color="#F70000">**对象**</font>元素说明

| 字段   | 类型   | 说明              |
| ------ | ------ | ----------------- |
| status  | Integer  | 状态，执行成功0，执行失败-1  |
| result  | String  | 无返回  |
| message  | String  | status -1 时返回错误消息  |



### <span id="switch_to_cross">当前用户切换为全仓模式</span>

- 地址

```http
POST /api/futures/switch_to_cross

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

- 请求参数

| 字段   | 类型   | 说明         | 备注                         |
| ------ | ------ | ------------ | ---------------------------- |
| coin_code | String | 结算币种 | 必填 |

- 响应

```json
{
	"status": 0,
	"result": null
}

```

- 返回<font color="#F70000">**对象**</font>元素说明

| 字段   | 类型   | 说明              |
| ------ | ------ | ----------------- |
| status  | Integer  | 状态，执行成功0，执行失败-1  |
| result  | String  | 无返回  |
| message  | String  | status -1 时返回错误消息  |



### <span id="switch_position_side">当前用户切换单双向持仓模式</span>

- 地址

```http
POST /api/futures/switch_position_side

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

- 请求参数

| 字段   | 类型   | 说明         | 备注                         |
| ------ | ------ | ------------ | ---------------------------- |
| coin_code | String | 结算币种 | 必填 |
| two_side_position | String | 双向持仓true, 单向持仓false | 必填 |

- 响应

```json
{
	"status": 0,
	"result": null
}

```

- 返回<font color="#F70000">**对象**</font>元素说明

| 字段   | 类型   | 说明              |
| ------ | ------ | ----------------- |
| status  | Integer  | 状态，执行成功0，执行失败-1  |
| result  | String  | 无返回  |
| message  | String  | status -1 时返回错误消息  |



### <span id="change_position_leverage">切换当前用户合约的持仓杠杆</span>

- 地址

```http
POST /api/futures/change_position_leverage

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

- 请求参数

| 字段   | 类型   | 说明         | 备注                         |
| ------ | ------ | ------------ | ---------------------------- |
| coin_code | String | 结算币种 | 必填 |
| symbol | String | 合约名字，BTCUSD, BCHUSD... | 必填 |
| leverage | Integer | 杠杆倍数 | 必填 |

- 响应

```json
{
	"status": 0,
	"result": null
}

```

- 返回<font color="#F70000">**对象**</font>元素说明

| 字段   | 类型   | 说明              |
| ------ | ------ | ----------------- |
| status  | Integer  | 状态，执行成功0，执行失败-1  |
| result  | String  | 无返回  |
| message  | String  | status -1 时返回错误消息  |



### <span id="risk_setting">修改当前用户的持仓</span>

- 地址

```http
POST /api/futures/risk_setting

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

- 请求参数

| 字段   | 类型   | 说明         | 备注                         |
| ------ | ------ | ------------ | ---------------------------- |
| coin_code | String | 结算币种 | 必填 |
| symbol | String | 合约名字，BTCUSD, BCHUSD... | 必填 |
| position_type | String | LONG为多仓，SHORT为空仓 | 必填 |
| add_deposit | Double | 追加/减少保证金，只对逐仓模式 | 可选 |
| stop_loss_price | Double | 止损价格 | 可选 |
| trailing_stop | Double | 追踪止损，不能和止损价格（stopLossPrice）同时设置 | 可选 |
| stop_win_price | Double | 止盈价格 | 可选 |
| stop_win_type | String | LIMIT为限价止盈，MARKET为市价止盈 | 可选 |

- 响应

```json
{
	"status": 0,
	"result": null
}

```

- 返回<font color="#F70000">**对象**</font>元素说明

| 字段   | 类型   | 说明              |
| ------ | ------ | ----------------- |
| status  | Integer  | 状态，执行成功0，执行失败-1  |
| result  | String  | 无返回  |
| message  | String  | status -1 时返回错误消息  |



### <span id="amend_order">修改当前用户的合约订单</span>

- 地址

```http
POST /api/futures/amend_order

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

- 请求参数

| 字段   | 类型   | 说明         | 备注                         |
| ------ | ------ | ------------ | ---------------------------- |
| order_no | String | 订单编号 | 必填 |
| quantity | Double | 数量 | 可选 |
| price | Double | 价格 | 可选 |
| trigger_price | Double | 条件单触发价 | 可选 |
| stop_loss_price | Double | 止损价格 | 可选 |
| trailing_stop | Double | 追踪止损，不能和止损价格（stopLossPrice）同时设置 | 可选 |
| stop_win_price | Double | 止盈价格 | 可选 |
| stop_win_type | String | LIMIT为限价止盈，MARKET为市价止盈 | 可选 |

- 响应

```json
{
	"status": 0,
	"result": null
}

```

- 返回<font color="#F70000">**对象**</font>元素说明

| 字段   | 类型   | 说明              |
| ------ | ------ | ----------------- |
| status  | Integer  | 状态，执行成功0，执行失败-1  |
| result  | String  | 无返回  |
| message  | String  | status -1 时返回错误消息  |



## 行情 REST API

### <span id="coin">获取币种</span>

- 地址

```http
GET /api/futures/public/basic/refData
```

- 请求参数

  （无）

- 响应

```json
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



### <span id="symbol">获取合约最新成交价</span>

- 地址

```http
GET /api/futures/public/basic/lastPrice
```

- 请求参数

  （无）

- 响应

```json
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

- 地址

```http
GET /api/futures/public/depth/depth
```

- 请求参数

| 字段   | 类型   | 说明                  | 备注 |
| ------ | ------ | --------------------- | ---- |
| symbol | String | 合约名称，eg: XBTCUSD | 必填 |

- 响应

```json
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

- 地址

```http
GET /api/futures/public/depth/trades
```

- 请求参数

| 字段     | 类型   | 说明                                                         | 备注 |
| -------- | ------ | ------------------------------------------------------------ | ---- |
| symbol   | String | 合约名称，eg: XBTCUSD                                        | 必填 |
| sequence | String | 上一次获取成交记录的最新一笔记录的 id；第一次请求传入空字符串 | 必填 |

- 响应

  返回最近50笔成交。

```json
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

- 地址

```http
GET /api/futures/public/kLine/byIndex
```

- 请求参数

| 字段   | 类型   | 说明                  | 备注 |
| ------ | ------ | --------------------- | ---- |
| symbol | String | 合约名称，eg: XBTCUSD | 必填 |
| type   | String | K线类型,eg: 5M 15M 1H | 必填 |
| step   | int    | 返回的数据量，eg:10   | 必填 |
| from   | Long   | 起始时间戳            | 必填 |

- 响应

```json
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

- 地址

```http
GET /api/futures/public/kLine/byTime
```

- 请求参数

| 字段   | 类型   | 说明                  | 备注 |
| ------ | ------ | --------------------- | ---- |
| symbol | String | 合约名称，eg: XBTCUSD | 必填 |
| type   | String | K线类型,eg: 5M 15M 1H | 必填 |
| step   | int    | 返回的数据量，eg:10   | 必填 |
| from   | Long   | 起始时间戳            | 可选 |

- 响应

```json
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

- 地址

```http
GET /api/futures/public/kLine/latest
```

- 请求参数

| 字段   | 类型   | 说明                  | 备注 |
| ------ | ------ | --------------------- | ---- |
| symbol | String | 合约名称，eg: XBTCUSD | 必填 |
| type   | String | K线类型,eg: 5M 15M 1H | 必填 |

- 响应

```json
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

- 地址

```http
GET /api/futures/public/kLine/fundingRate
```

- 请求参数

| 字段    | 类型   | 说明                                              | 备注 |
| ------- | ------ | ------------------------------------------------- | ---- |
| symbols | String | 合约名称，多个合约以逗号分隔，eg: XBTCUSD,XETHUSD | 必填 |

- 响应

```json
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

- 地址

```http
GET /api/futures/public/kLine/tradeStatistics
```

- 请求参数

| 字段    | 类型   | 说明                                              | 备注 |
| ------- | ------ | ------------------------------------------------- | ---- |
| symbols | String | 合约名称，多个合约以逗号分隔，eg: XBTCUSD,XETHUSD | 必填 |

- 响应

```json
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

- 地址

```http
GET /api/futures/public/kLine/openInterest
```

- 请求参数

| 字段   | 类型   | 说明                                                         | 备注 |
| ------ | ------ | ------------------------------------------------------------ | ---- |
| symbol | String | 合约名称，eg: BTCUSD （注意：和合约名称进行区分，标记名前去掉X） | 必填 |

- 响应

```json
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



##  行情 WebSocket API 

#### 链接

***wss://api.hubi.com/ws/futures/public/market*** 

或 

***wss://api.hubi.pub/ws/futures/public/market（国内）***

### 指令格式

#### 订阅数据更新

订阅数据的动态更新，需要指定具体的 channel，以及需要的参数

```json
{"op":"subscribe", "channel":"xxxxxx", ...}
```

#### 数据更新

根据订阅返回更新数据，event 为对应订阅的 channel 路径

```json
{"event": "xxxxxx", ...}
```

#### 取消订阅

取消订阅数据的动态更新，与订阅类似，同样需要指定具体的 channel，以及需要的参数

```json
{"op":"unsubscribe", "channel":"xxxxxx", ...}
```

###  标记价

订阅格式如下：

```json
{"op":"subscribe", "channel":"/api/index/price", "key":"BTCUSD"}
```

数据更新格式如下：

```json
{
    "key": "BTCUSD", 
    "event": "/api/index/price", 
    "value": 9482.89925, 
    "updatedTime": "Jun 17, 2020 09:13:43 AM"
}
```

取消订阅：

```json
{"op":"unsubscribe", "channel":"/api/index/price", "key":"BTCUSD"}
```

### 深度

订阅格式如下：

```json
{"op":"subscribe", "channel":"/api/depth/depth", "key":"XBTCUSD"}
```

数据更新格式如下：

```json
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

```json
{"op":"unsubscribe", "channel":"/api/depth/depth", "key":"XBTCUSD"}
```

### 资金费率

订阅格式如下：

```json
{"op":"subscribe", "channel":"/api/kLine/fundingRate", "key":"XBTCUSD"}
```

数据更新格式如下：

```json
{
    "key": "XBTCUSD", 
    "event": "/api/kLine/fundingRate", 
    "rate": -0.0001, 
    "date": "Jun 17, 2020 12:00:00 PM"
}
```

取消订阅：

```json
{"op":"unsubscribe", "channel":"/api/kLine/fundingRate", "key":"XBTCUSD"}
```

### 持仓量

订阅格式如下：

```json
{"op":"subscribe", "channel":"/api/kLine/openInterest", "key":"BTCUSD"}
```

数据更新格式如下：

```json
{
    "key": "BTCUSD", 
    "event": "/api/kLine/openInterest", 
    "value": 6323.8854145971545, 
    "date": "Jun 17, 2020 09:42:26 AM", 
    "qty": 60000677
}
```

取消订阅：

```json
{"op":"unsubscribe", "channel":"/api/kLine/openInterest", "key":"BTCUSD"}
```

### 24小时成交统计

订阅格式如下：

```json
{"op":"subscribe", "channel":"/api/kLine/tradeStatistics", "key":"XBTCUSD"}
```

数据更新格式如下：

```json
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

```json
{"op":"unsubscribe", "channel":"/api/kLine/tradeStatistics", "key":"XBTCUSD"}
```

###  K 线更新

订阅格式如下：

```json
{"op":"subscribe", "channel":"/api/kLine/kLine", "key":"XBTCUSD", "type":"1M"}
```

数据更新格式如下：

```json
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

```json
{"op":"unsubscribe", "channel":"/api/kLine/kLine", "key":"XBTCUSD", "type":"1M"}
```



## 错误代码列表

| 错误码             |                描述                |
| ------------------ | :--------------------------------: |
| ILLEGAL_NONCE      |      X-API-Nonce 值不合法错误      |
| ILLEGAL_VERSION    |             版本不兼容             |
| ILLEGAL_TIMESTAMP  |             时间戳错误             |
| ILLEGAL_SIGN       |              签名错误              |
| ILLEGAL_ARGUMENT   |              参数错误              |
| ILLEGAL_SIGN_TYPE  |           签名类型不正确           |
| ILLEGAL_CHARSET    |            字符集不合法            |
| ILLEGAL_ACCESS_KEY |           访问密钥不正确           |
| ILLEGAL_SCOPE      | 访问范围不合法的，（只读或者读写） |
| ILLEGAL_CLIENT_IP  |           IP 地址被限制            |



