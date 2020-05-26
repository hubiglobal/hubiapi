# HUBI API 文档

##  接入准备

您需要先登录[网页端](https://www.hubi.com/zh/api/setting)申请API key，必要的话须设置访问白名单权限，默认不限制。

创建成功后请务必记住以下信息：

`API Key` API访问唯一标识

`Secret Key`  签名认证加密所使用的密钥（仅申请时可见）

`Access Token` 用户身份标识（仅申请时可见）

## 接口鉴权

Hubi提供的接口包括公共接口和私有接口两种类型。

公共接口可用于获取行情数据，无需认证即可调用。

私有接口可用于获取基础数据、交易管理，每个私有请求必须使用 API Key进行签名。

## 接入URLs

| 类型        | URL                              | 说明                                      |
| ----------- | -------------------------------- | ----------------------------------------- |
| RESTful     | https://api.hubi.com             |                                           |
| WebSocket   | wss://api.hubi.com/ws/connect/v1 |                                           |
| *WebSocket* | *wss://api.hubi.com/was*         | <font color="#F70000">**即将废弃**</font> |

## 签名

签名说明

API 请求在通过 internet 传输的过程中极有可能被篡改，为了确保请求未被更改，除公共接口（行情数据）外的私有接口均必须使用您的 API Key 做签名认证，以校验参数或参数值在传输途中是否发生了更改。

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



## 接口索引

### RESTful API

URL: https://api.hubi.com

| 接口                                                         | 说明                               |
| ------------------------------------------------------------ | ---------------------------------- |
| [`GET /api/user`](#user)                                     | 获取用户信息                     |
| [`POST /api/asset/user/customer_asset_info/additional`](#asset) | 查询资产                         |
| [`GET /api/coin/coin_base_info/simple`](#coin)               | 获取币种                         |
| [`GET /api/coin/coin_pairs_param/pairses`](#symbol)          | 获取交易对                       |
| [`POST /api/engine/entrust/fix`](#fix)                              | 下限价委托单                     |
| [`PUT /api/engine/entrust/cancel/v1`](#revocation)                            | 撤销委托                         |
| [`POST /api/engine/entrust/cancel/batch/v1`](#revocation_batch)                            | 批量撤销委托                         |
| [`POST /api/entrust/current/top`](#current) | 查询当前委托 |
| [`POST /api/entrust/exchange/slice/history/user`](#history)        | 查询历史委托   |
| [`GET /api/entrust/info`](#entrust_info) | 根据委托单号查询委托             |
| [`GET /api/entrust/order/info`](#order_info) | 根据委托单号查询成交单          |
| [`GET /api/connect/public/market/depth/{symbol}`](#market_depth) | 买卖盘行情 |
| [`GET /api/connect/public/market/trade/{symbol}`](#market_trade) | 成交行情 |

### WebSocket

URL: wss://api.hubi.com/ws/connect/v1

原来的 `wss://api.hubi.com/was`（即将废弃）

Hubi 的websocket订阅只有一个地址 `wss://api.hubi.com/ws/connect/v1`，支持以下订阅

| 主题                             | 推送方式 |
| -------------------------------- | -------- |
| [`买卖盘行情`](#depth)           | 全量推送 |
| [`成交行情`](#trade)             | 增量推送 |
| [`24小时成交量`](#statistics_24) | 增量推送 |


## 基础信息查询


### <span id="coin">获取币种</span>

- 地址

```http
GET /api/coin/coin_base_info/simple
```
- 请求参数

  （无）
  
- 响应

```json
[
    { "id": "7KG95RD10U1KHDDCE4U8", "coinCode": "BTC" },
    { "id": "7KG95RD10U1KHDDCE4U9", "coinCode": "USDT" },
]
```


- 返回对象说明

| 字段     | 类型   | 说明     |
| -------- | ------ | -------- |
| id       | String |          |
| coinCode | String | 货币代码 |



### <span id="symbol">获取交易对</span>

- 地址

```http
GET /api/coin/coin_pairs_param/pairses
```

- 请求参数

  （无）

- 响应

```json
[
    {
        "coinCode": "USDT",
        "coinParisStr": "BTC_USDT,ETH_USDT",
        "coinPairs": [
            {
                "id": "8a80803267a2ed080167a6be32391d19",
                "version": 115,
                "createdBy": "system",
                "createdTime": "2018-12-13 16:45:42",
                "lastModifiedBy": "02",
                "lastModifiedTime": "2020-01-13 14:31:36",
                "coinPairsNumber": 10011000,
                "coinCode": "BTC",
                "priceCoinCode": "USDT",
                "buyFeeRate": 1,
                "sellFeeRate": 2,
                "maxIncrease": 100,
                "maxDecline": 100,
                "minOrderLimit": 0.001,
                "maxOrderLimit": 10,
                "pairsPricePrecision": 9,
                "pairsVolumePrecision": 10,
                "marketBuyMin": 0.01,
                "marketBuyMax": 100,
                "marketSellMin": 0.01,
                "marketSellMax": 100,
                "marketMatchDepth": 5,
                "closeEnable": false,
                "tradeStatus": true,
                "yesterdayClosePrice": 3380,
                "checkStatus": true,
                "commission": null,
                "openPrice": 80,
                "sort": null,
                "defaultCoinPairs": null,
                "unionPairsTradeStatus": false,
                "marketRegion": "MAIN_MARKET"
            },
            {
                "id": "8a80803267a2ed080167a6be5e9f1d25",
                "version": 101,
                "createdBy": "system",
                "createdTime": "2018-12-13 16:45:53",
                "lastModifiedBy": "02",
                "lastModifiedTime": "2020-01-15 11:22:11",
                "coinPairsNumber": 10031000,
                "coinCode": "ETH",
                "priceCoinCode": "USDT",
                "buyFeeRate": 0.1,
                "sellFeeRate": 0.2,
                "maxIncrease": 50,
                "maxDecline": 50,
                "minOrderLimit": 0.01,
                "maxOrderLimit": 100,
                "pairsPricePrecision": 6,
                "pairsVolumePrecision": 5,
                "marketBuyMin": 0.01,
                "marketBuyMax": 100,
                "marketSellMin": 0.01,
                "marketSellMax": 100,
                "marketMatchDepth": 5,
                "closeEnable": false,
                "tradeStatus": true,
                "yesterdayClosePrice": 89.03,
                "checkStatus": true,
                "commission": null,
                "openPrice": 89.03,
                "sort": null,
                "defaultCoinPairs": null,
                "unionPairsTradeStatus": true,
                "marketRegion": "INNOVATE_MARKET"
            }
        ],
        "regionSymbols": null
    }
]
```

- 返回<font color="#F70000">**数组**</font>元素说明

| 字段                 | 类型    | 说明             |
| -------------------- | ------- | ---------------- |
| coinCode         | String  | 市场         |
| coinParisStr         | String  | 市场所开交易对字符串 |
| coinPairs            | Array |                  |
| coinPairs[0].coinCode | String | 交易币 |
| coinPairs[0].priceCoinCode     | String | 定份币 |
| coinPairs[0].buyFeeRate        | number  | 买方手续费率 |
| coinPairs[0].sellFeeRate       | number  | 卖方手续费率 |
| coinPairs[0].maxIncrease       | number  | 限价单笔最大涨幅 |
| coinPairs[0].maxDecline        | number  | 限价单笔最大跌幅 |
| coinPairs[0].minOrderLimit     | number  | 单笔最小下单量 |
| coinPairs[0].maxOrderLimit     | number  | 单笔最大下单量 |
| coinPairs[0].pairsPricePrecision | number  | 交易对价格小数位 |
| coinPairs[0].pairsVolumePrecision | number  | 交易对数量小数位 |
| coinPairs[0].marketBuyMin      | number  | 市价最小买入量   |
| coinPairs[0].marketBuyMax      | number  | 市价最大买入量   |
| coinPairs[0].marketSellMin     | number  | 市价最小卖出量   |
| coinPairs[0].marketSellMax     | number  | 市价最大卖出量   |
| coinPairs[0].tradeStatus       | boolean | 交易状态         |
| coinPairs[0].openPrice         | number  | 开盘价           |
| coinPairs[0].marketRegion      | String  | 市场分区         |


### <span id="asset">查询资产</span>

- 地址

```http
POST /api/asset/user/customer_asset_info/additional

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

- 请求参数

| 字段            | 类型   | 说明     | 备注                 |
| --------------- | ------ | -------- | -------------------- |
| page   | Number | 委托价格 | 非必填，从0开始           |
| size   | Number | 委托数量 | 非必填，默认10         |

- 响应

```json
{
    "content": [
        {
            "id": "EFW13E11QZX6XTGCVSW0",
            "customerId": "01001000035k4",
            "realName": null,
            "coinCode": "ETH",
            "coinIcon": null,
            "availableAmount": 134.7534,
            "frozenAmount": 0,
            "lockedAmount": 0,
            "totalAmount": 134.7534,
            "status": false,
            "publicKey": null,
            "version": 38,
            "createdBy": "system",
            "createdTime": null,
            "lastModifiedBy": "system",
            "lastModifiedTime": null,
            "accountInfoId": null,
            "memo": null,
            "remark": null,
            "availableAmountStr": "134.7534",
            "frozenAmountStr": "0",
            "lockedAmountStr": "0"
        },
        {
            "id": "751ce40c22fe11ea93bb00163e00a5d4",
            "customerId": "01001000035k4",
            "realName": null,
            "coinCode": "USDT",
            "coinIcon": null,
            "availableAmount": 9999887693.2431638,
            "frozenAmount": 4692,
            "lockedAmount": 0,
            "totalAmount": 9999892385.2431638,
            "status": false,
            "publicKey": null,
            "version": 66,
            "createdBy": "system",
            "createdTime": null,
            "lastModifiedBy": "01001000035k4",
            "lastModifiedTime": null,
            "accountInfoId": null,
            "memo": null,
            "remark": null,
            "availableAmountStr": "9999887693.2431638",
            "frozenAmountStr": "4692",
            "lockedAmountStr": "0"
        }
    ],
    "numberOfElements": 2,
    "size": 2,
    "number": 0
}

```

- 返回对象说明

| 字段              | 类型   | 说明                   |
| ----------------- | ------ | ---------------------- |
| size | int | 每页大小 |
| number | int | 当前返回的页数,从0开始 |
| numberOfElements | int | content的元素个数 |
| content | Array |  |
| content[0].coinCode | String | 币种             |
| content[0].availableAmount | Number   | 可用资金 |
| content[0].frozenAmount | Number   | 冻结资金 |
| content[0].lockedAmount | Number   | 锁定资金 |
| content[0].totalAmount | Number   | 总资产 |


## 交易接口

### <span id="fix">限价委托</span>

- 地址

```http
POST /api/engine/entrust/fix

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

- 请求参数

| 字段            | 类型   | 说明                   | 备注                 |
| --------------- | ------ | ---------------------- | -------------------- |
| coin_code       | String | 交易币                 | 必填                 |
| price_coin_code | String | 定价币                 | 必填                 |
| entrust_price   | Number | 委托价格               | 必填                 |
| entrust_count   | Number | 委托数量               | 必填                 |
| direction       | String | 交易方向(`BUY`或`SELL`) |必填 |
| trade_password  | String | 资金密码               | 非必填，配合交易策略 |

- 响应

```json
{ 
    "entrustNumber": "139669789713891328", 
    "entrustMillisTime": 1579571872807 
}
```

- 返回对象说明

| 字段              | 类型   | 说明                   |
| ----------------- | ------ | ---------------------- |
| entrustNumber     | String | 委托单号               |
| entrustMillisTime | long   | 下委托时间，精确到毫秒 |

- 错误码

| message                        | 解释                           |
| ------------------------------ | ------------------------------ |
| entrust_price_illegal          | 委托价格不能为空且必须大于0    |
| entrust_price_scala_too_large  | 委托价格小数位太多             |
| entrust_count_illegal          | 委托数量不能为空且必须大于0    |
| entrust_amount_scala_too_large | 委托数量小数位太多             |
| entrust_exchange_trade_close   | 交易所关闭交易/联盟关闭交易    |
| entrust_price_scala_mismatch   | 委托价格小数位与后台配置不匹配 |
| entrust_amount_scala_mismatch  | 委托数量小数位与后台配置不匹配 |
| entrust_amount_gt_max_allow    | 委托下单量大于最大交易量       |
| entrust_amount_lt_min_allow    | 委托下单量小于最小交易量       |
| entrust_trade_deny             | 用户被禁止交易                 |
| entrust_fund_password_error    | 用户资金密码错误               |
| INVALID_PRICE_RANGE            | 价格超出上限或下限             |
| NOT_ACCEPTED                   | 撮合关闭                       |
| fund_not_enough                | 资金不足                       |



### <span id="revocation">撤销委托</span>

- 地址

```http
PUT /api/engine/entrust/cancel/v1

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

- 请求参数


| 字段            | 类型   | 说明     | 备注 |
| --------------- | ------ | -------- | ---- |
| entrust_numbers | String | 委托单号 | 必填 |


- 响应
```json
{
    "entrustNum": "139669789713891328",
    "message": "ACCEPTED"
}
```

- 返回对象说明

| 字段       | 类型   | 说明        |
| ---------- | ------ | ----------- |
| entrustNum | String | 委托单号    |
| message    | String | 错误/成功码 |

- resultCode解释

| message  | 解释                       |
| -------- | -------------------------- |
| ACCEPTED | 接受了撤销请求             |
| CANCELED | 委托已经撤销               |
| FILLED   | 委托已经成交               |
| FAIL     | 委托之前已经标记为撤销失败 |

- 错误码

| message                      | 解释           |
| ---------------------------- | -------------- |
| entrust_trade_deny           | 用户被禁止交易 |
| entrust_exchange_trade_close | 交易所关闭交易 |



### <span id="revocation_batch">批量撤销委托</span>

- 地址

```http
POST /api/engine/entrust/cancel/batch/v1

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

- 请求参数

| 字段            | 类型   | 说明     | 备注                       |
| --------------- | ------ | -------- | -------------------------- |
| entrust_numbers | String | 委托单号 | 必填，多个单号使用逗号分隔 |

- 响应

```json
[
    {
        "entrustNum": "139669789713891328",
        "message": "CANCELED"
    }
]

```

- 返回对象说明

| 字段       | 类型   | 说明        |
| ---------- | ------ | ----------- |
| entrustNum | String | 委托单号    |
| message    | String | 错误/成功码 |

- message说明

| message  | 解释                       |
| -------- | -------------------------- |
| ACCEPTED | 撮合接受了撤销请求         |
| CANCELED | 委托已经撤销               |
| FILLED   | 委托已经成交               |
| FAIL     | 委托之前已经标记为撤销失败 |
|          |                            |

- 错误码

| message                      | 解释           |
| ---------------------------- | -------------- |
| entrust_trade_deny           | 用户被禁止交易 |
| entrust_exchange_trade_close | 交易所关闭交易 |




### <span id="current">查询当前委托</span>

-  地址

```http
POST /api/entrust/current/top

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```


- 请求参数


| 字段            | 类型   | 说明     | 备注    |
| --------------- | ------ | -------- | ------- |
| top             | int    | 请求行数 | 最大500 |
| coin_code       | String | 交易币   |         |
| price_coin_code | String | 定价币   |         |


- 响应

```json
[
    {
        "id": "FAQB52E1XRZOBLVB0XDS",
        "surplusCount": 11.0,
        "transactionCount": 0e-10,
        "canceledAmount": 0e-10,
        "no": "139672783490678784",
        "coinCode": "ETH",
        "priceCoinCode": "USDT",
        "way": "FIXED",
        "direction": "SELL",
        "price": 120.0,
        "count": 11.0,
        "millisTime": 1579572586579,
        "status": "SUBMITTED"
    },
    {
        "id": "FAQAJ501XRZOBLVB0XDS",
        "surplusCount": 11.0,
        "transactionCount": 0e-10,
        "canceledAmount": 0e-10,
        "no": "139672664322080768",
        "coinCode": "ETH",
        "priceCoinCode": "USDT",
        "way": "FIXED",
        "direction": "BUY",
        "price": 110.0,
        "count": 11.0,
        "millisTime": 1579572558167,
        "status": "SUBMITTED"
    }
]

```

- 返回<font color="#F70000">**数组**</font>元素说明

| 字段             | 类型   | 说明                             |
| ---------------- | ------ | -------------------------------- |
| no               | String | 委托单号                         |
| coinCode         | String | 交易币                           |
| priceCoinCode    | String | 定价币                           |
| way              | String | 下单类型（限价，市价，止盈止损） |
| direction        | String | 买卖方向， 0：买，1：卖          |
| price            | Number | 委托价格                         |
| count            | Number | 委托数量                         |
| millisTime       | long   | 下委托时间，精确到毫秒           |
| surplusCount     | Number | 剩余委托数量                     |
| transactionCount | Number | 成交量                           |
| canceledAmount   | Number | 撤销量                           |
| status           | String | 状态                             |

- 状态说明

| 状态             | 说明             |
| ---------------- | ---------------- |
| SUBMITTED        | 未成交           |
| PARTIAL_FILLED   | 部分成交         |
| PARTIAL_CANCELED | 部分成交部分撤销 |
| FILLED           | 完全成交         |
| CANCELED         | 已撤销           |




### <span id="history">查询历史委托</span>

- 地址

```http
POST /api/entrust/exchange/slice/history/user

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```


- 请求参数

| 字段            | 类型    | 说明           | 备注                        |
| --------------- | ------- | -------------- | --------------------------- |
| page            | int     | 请求页         | 非必填，从0开始             |
| size            | int     | 每页大小       | 非必填，默认10              |
| coin_code       | String  | 交易币         | 非必填                      |
| price_coin_code | String  | 定价币         | 非必填                      |
| direction       | String  | 买卖方向       | 非必填                      |
| begin_time      | Date    |                | 非必填，yyyy-MM-dd HH:mm:ss |
| end_time        | Date    |                | 非必填，yyyy-MM-dd HH:mm:ss |
| filter_canceled | boolean | 是否过滤撤销的 | 非必填，默认false           |

- 响应

```json
{
    "content": [
        {
            "coinCode": "ETH",
            "direction": "BUY",
            "entrustCount": 11.0,
            "entrustNumber": "139669789713891328",
            "entrustPrice": 110.0,
            "entrustSum": 1210.0,
            "entrustTime": "2020-01-21 09:57:52",
            "entrustWay": "FIXED",
            "priceCoinCode": "USDT",
            "processedPrice": 0,
            "status": "CANCELED",
            "surplusEntrustAmount": 0e-10,
            "transactionSum": 0e-10,
            "turnover": 0e-10
        }
    ],
    "numberOfElements": 1,
    "size": 10,
    "number": 0
}

```

- 返回对象说明

| 字段                      | 类型   | 说明              |
| ------------------------- | ------ | ----------------- |
| size                      | int    | 每页记录数        |
| number                    | int    | 当前页数，从0开始 |
| numberOfElements          | int    | content元素个数   |
| content[]                 | Array  |                   |
| content[0].entrustNumber  | String | 委托单号          |
| content[0].coinCode       | String | 交易币            |
| content[0].priceCoinCode  | String | 定价币            |
| content[0].direction      | String | 买卖方向          |
| content[0].entrustPrice   | Number | 委托价            |
| content[0].entrustCount   | Number | 委托数量          |
| content[0].entrustTime    | Date   | 委托时间          |
| content[0].entrustWay     | String | 委托类型          |
| content[0].turnover       | Number | 成交数量数量      |
| content[0].processedPrice | Number | 成交均价          |
| content[0].status         | String | 委托单状态        |

  

### <span id="entrust_info">根据委托单号查询委托</span>

- 地址

```http
GET /api/entrust/info
```

- 请求参数

| 字段           | 类型   | 说明     | 备注 |
| -------------- | ------ | -------- | ---- |
| entrust_number | String | 委托单号 | 必填 |

- 响应

```json
{
    "id": "FAQB52E1XRZOBLVB0XDS",
    "surplusCount": 11.0,
    "transactionCount": 0e-10,
    "canceledAmount": 0e-10,
    "no": "139672783490678784",
    "coinCode": "ETH",
    "priceCoinCode": "USDT",
    "customerId": "01001000035k4",
    "way": "FIXED",
    "direction": "SELL",
    "price": 120.0,
    "count": 11.0,
    "transactionSum": 0e-10,
    "triggerPrice": null,
    "millisTime": 1579572586579,
    "processedPrice": 0,
    "entrustTime": "2020-01-21 10:09:46",
    "status": "SUBMITTED"
}

```

- 返回对象说明

| 字段                | 类型   | 说明         |
| --------------        | ------ | ------------ |
| surplusCount          | Number | 剩余数量         |
| transactionCount      | Number | 成交数量         |
| canceledAmount        | Number | 撤销数量     |
| direction             | String | 买卖方向         |
| coinCode              | String | 交易币          | 
| priceCoinCode         | String | 定价币      |
| customerId            | String | 用户ID         |
| way                   | String | 委托类型         |
| price                 | Number | 委托价格         |
| count                 | Number | 委托数量         |
| transactionSum        | Number | 成交总额     |
| triggerPrice          | Number | 触发价          |
| millisTime            | long   | 下单时间戳，毫秒   |
| processedPrice        | Number | 成交均价     |
| entrustTime           | Date   | 下单时间     |
| status                | String | 委托单状态    |

  

### <span id="order_info">根据委托单号查询成交单</span>

- 地址

```http
GET /api/entrust/order/info
```

- 请求参数

| 字段           | 类型   | 说明     | 备注   |
| -------------- | ------ | -------- | ------ |
| entrust_number | String | 委托单号 | 必填， |

- 响应

```json
[
    {
        "id": 139675491006054401,
        "orderNumber": "139675489479213056",
        "coinCode": "ETH",
        "priceCoinCode": "USDT",
        "entrustWay": "FIXED",
        "entrustPrice": 110.0,
        "transactionPrice": 110.0,
        "transactionCount": 1.0,
        "transactionTimeStamp": 1579573231736,
        "transactionSum": 110.0,
        "transactionFee": 0.22,
        "transactionFeeRate": 0.002,
        "triggerPrice": null,
        "entrustNumber": "139675489282326528",
        "custId": "01001000035k4",
        "direction": "SELL",
        "entrustCategory": "user",
        "transactionTime": "2020-01-21 10:20:31"
    }
]

```

- 返回对象说明

| 字段               | 类型   | 说明     |
| ------------------ | ------ | -------- |
| custId             | String | 用户ID   |
| entrustNum         | String | 委托单号 |
| orderNumber        | String | 成交单号 |
| coinCode           | String | 交易币   |
| priceCoinCode      | String | 定价币   |
| direction          | String | 买卖方向 |
| entrustPrice       | Number | 委托价   |
| transactionPrice   | Number | 成交价   |
| transactionCount   | Number | 成交量   |
| transactionFee     | Number | 手续费   |
| transactionFeeRate | Number | 手续费率 |
| transactionSum     | Number | 成交量   |

  

## <span id="was">行情</span>



### <span id="market_depth">买卖盘行情</span>

- 地址

```http
GET /api/connect/public/market/depth/{symbol}
```

- 请求参数

| 字段   | 类型   | 说明         | 备注                         |
| ------ | ------ | ------------ | ---------------------------- |
| symbol | String | 请求的交易对 | 必填，eth_usdt，大小定不敏感 |

- 响应

```json
{
    "asks": [
        {
            "amount": "11.0000000000",
            "price": "120.0000000000",
            "time": 1579573622553,
            "volume": "11.0000000000"
        }
    ],
    "bids": [
        {
            "amount": "11.0000000000",
            "price": "110.0000000000",
            "time": 1579573622553,
            "volume": "11.0000000000"
        }
    ],
    "last_strike_price": "110.0000000000",
    "timestamp": 1579573622833
}


```

- 返回对象说明

| 字段               | 类型   | 说明     |
| ------------------ | ------ | -------- |
| asks | Array | 按price降序 |
| asks.price              | String | 价格档   |
| asks.volume             | String | 档位的量 |
| bids | Array | 按price降序 |
| bids.price              | String | 价格档   |
| bids.volume             | String | 档位的量 |
| last_strike_price | String | 最新成交价 |
| timestamp | long | 时间戳，精确到毫秒 |

 

### <span id="market_trade">交易记录行情</span>

- 地址

```http
GET /api/connect/public/market/trade/{symbol}
```

- 请求参数

| 字段   | 类型   | 说明         | 备注                         |
| ------ | ------ | ------------ | ---------------------------- |
| symbol | String | 请求的交易对 | 必填，eth_usdt，大小定不敏感 |
| size   | int    | 请求的长度   | 非必填，默认30               |

- 响应

```json
[
    {
        "id": "139675489479213056",
        "price": "110.0000000000",
        "symbol": "eth_usdt",
        "time": 1579573231736,
        "type": "ASK",
        "volume": "1.0000000000"
    },
    {
        "id": "135800181625688064",
        "price": "151.0000000000",
        "symbol": "eth_usdt",
        "time": 1578649286344,
        "type": "ASK",
        "volume": "0.4000000000"
    }
]

```

- 返回<font color="#F70000">**数组**</font>元素说明

| 字段   | 类型   | 说明              |
| ------ | ------ | ----------------- |
| id     | String |                   |
| price  | String | 成交价            |
| symbol | String | 交易对            |
| time   | long   | 成交时间戳        |
| type   | String | 成交类型（买\卖） |
| volume | String | 成交量            |

 

##  WebSocket行情



### <span id="depth">买卖盘行情</span>

- 订阅参数：

```json
  {
    "channel": "depth_all",
    "symbol": "btcusdt"//只支持一个交易对
  }
```

- 响应状态:

```json
ACCEPTED:{ "channel": "depth_all", "symbol": "btcusdt" }
```

- 返回对象说明

| 字段              | 类型   | 说明 |
| ----------------- | ------ | ---- |
| data | Object |  |
| data.asks          | Array | 按price降序排列 |
| data.asks.amount     | String | 量；等于volume（兼容原接口） |
| data.asks.price        | String | 价格 |
| data.asks.volume       | String | 量 |
| data.bids            | Array | 按price降序排列 |
| data.bids.amount      | String | 量；等于volume（兼容原接口） |
| data.bids.price        | String | 价格 |
| data.bids.volume       | String | 量 |
| data.last_strike_price | String | 最新成交价 |
| dataType                  | String | 等于订阅参数的`channel` |
| symbol                  | String | 等于订阅参数的`symbol` |
| timestamp                  | long | 时间戳，精确到毫秒 |




### <span id="trade">交易记录行情</span>

- 订阅参数：

```json
{
    "channel": "trade_history",
    "symbol": "btcusdt"//多个交易对使用英文逗号分隔
}
```

- 响应状态:

```json
ACCEPTED:{ "channel": "trade_history", "symbol": "btcusdt", "bourse": "01001" }
```

- 返回对象说明

| 字段   | 类型   | 说明 |
| ------ | ------ | ---- |
| data | Object |  |
| data.curentExchangePrice  | String | 最新成交价 |
| data.trades  | Array | 交易记录 |
| data.trades.amount | String | 成交量;等于volume（兼容原接口） |
| data.trades.id | String | 订单ID |
| data.trades.price | String | 订单成交价 |
| data.trades.symbol | String | 订单交易对 |
| data.trades.time | long | 成交时间，精确到毫秒 |
| data.trades.type | String | 成交类型（买\|卖） |
| data.trades.volume | String | 成交量；等于amount |
| dataType | String | 等于订阅参数的`channel` |
| symbol | String | 等于订阅参数的`symbol` |
| timestamp | long | 时间戳，精确到毫秒 |



### <span id="statistics_24">24小时成交量</span>

- 订阅参数

```json
{
    "channel":"statistics_24",
    "symbol":"HUB_USDT,BTC_USDT"//多个交易对使用英文逗号分隔
}
```

- 响应状态

```json
ACCEPTED:{ "channel":"statistics_24", "symbol":"HUB_USDT,BTC_USDT,ETH_USDT,LTC_USDT,EOS_USDT,ETH_BTC" }
```
```json
{"code":406,"message":"NOT_ACCEPTABLE","sid":"7bfdceea"}
```

- 返回对象说明

| 字段   | 类型   | 说明 |
| ------ | ------ | ---- |
| data | Array |  |
| data.high   | String | 24小时内最高价 |
| data.low    | String | 24小时内最低价 |
| data.new    | String | 最新成交价，等于price（兼容原接口） |
| data.price  | String | 最新成交价 |
| data.rate   | String | 涨跌幅 |
| data.symbol | String | 交易对 |
| data.time   | String | 时间，精确到秒 |
| data.volume | String | 成交总量 |
| dataType | String | 等于订阅参数的`channel` |
| symbol | String | 等于订阅参数的`symbol` |
| timestamp | long | 时间戳，精确到毫秒 |


