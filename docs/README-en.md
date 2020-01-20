# HUBI API Documentation

##  Preparation

You need to sign in [hubi.com](https://www.hubi.com/zh/api/setting) to apply for the API key. If necessary, you can set the IP whitelist if necessary.

Be sure to keep the following information in mind after creating:

`API Key` API Access Key

`Secret Key`  The key used for signature authentication encryption (visible on application only)

`Access Token` User id card (visible for application only)

## Interface authentication

The interfaces provided by Hubi include both public and private interfaces.

The public interface can be used to obtain market data and can be invoked without authentication.
The private interface can be used to obtain the underlying data, transaction management, and each private request must be signed using the API Key.

## Access to the URLs

| Type        | URL                              | Description                                 |
| ----------- | -------------------------------- | ------------------------------------------- |
| RESTful     | https://api.hubi.com             |                                             |
| WebSocket   | wss://api.hubi.com/ws/connect/v1 |                                             |
| *WebSocket* | *wss://api.hubi.com/was*         | <font color="#F70000">**deprecated**</font> |

## Signature

API requests are likely to be tampered with in the process of Internet transmission. In order to ensure that the requests are not changed, all private interfaces except the public interface (quotation data) must use your API key for signature authentication to verify whether the parameters or parameter values have changed during transmission.
A valid request header should contain the following information：

- `X-API-Version`： Requested API version, which now only supports 1.0.0.

- `X-API-Key`： Access Key，The Access Key in the API Key you applied for.

- `X-API-Timestamp`： Request timestamp, ISO format, e.g. : 2018-07-18t01:25:47.048z, string.

- `X-API-Nonce`： calculated by md5 (client_id + timestamp + seqNum), client_id is the applied access key, timestamp is the request timestamp, and seqNum is a string of random numbers.

- `X-API-Signature-Params`：parameters that need to be signed, separated by commas, such as top, coin_code, price_coin_code

- `X-API-Signature`：The signature result is obtained through HmacSHA256 (client_secret, params + version + nonce + url), client_secret is the Secret Key in the API Key you applied for, and params is the parameter key-value pair involved in signing. "Key = value &" to connect, such as top = 100 & coin_code = HUB & price_coin_code = USDT, sorted according to the parameter order specified by `X-API-Signature-Params`, version = 1.0.0, no plus sign (+) is required when the fields are connected See examples for details.

- `Authorization`： <font color=#8E25B8>Bearer</font> access_token，  fixed by Bearer, access_token is the access token in the API Key you applied for.

<font color="#F70000">**The parameters of GET, PUT, and POST requests all need to participate in the signature calculation **</font>

### Examples

*(Note the relationship between texts of the same color)*

`URL`：POST /api/entrust/current/top

`参数`：top=100,coin_code=HUB,price_coin_code=USDT

`Access Key` : <font color=#AD18C7>14e5aa14f20345cbaf020e9b8562cbd6</font>

`Secret Key` : <font color="#8C192C">b3a0a2a36d0f4b52b697ac2df3484bc2</font>

`Access Token` : <font color="#0BA118">b868dd65-84da-43f5-b2ca-d3dcc843e795</font>

**steps**

1. Set the random number seqNum = 999 (It is recommended to use an auto-incrementing sequence, which is incremented by 1 for each request.)
2. Get current time to get timestamp = 2019-12-30T15: 52: 41.788
3. Calculate MD5(<font color=#AD18C7>14e5aa14f20345cbaf020e9b8562cbd6</font><font color="#198C25">2019-12-30T15:52:41.788</font><font color="#D6042B">999</font>)， and get nonce  = 3c72aa1b1d0b486b4bcd9350e9410ad5
4. HmacSHA256 signature，HmacSHA256(<font color="#8C192C">b3a0a2a36d0f4b52b697ac2df3484bc2</font>, <font color="#0F0778">top=100&coin_code=HUB&price_coin_code=USDT</font><font color="#FC1D00">1.0.0</font><font color="#D6A504">3c72aa1b1d0b486b4bcd9350e9410ad5</font><font color="#8604D6">/api/entrust/current/top</font>)， and get signature = ab8c4d4535cf8d33283462d6c8571b8ca4241b608fc77659a1be2d6dae9709b2

*md5, sha256  encrypted character set using UTF-8*

最后得到 header

- `X-API-Version`： 1.0.0
- `X-API-Key`： <font color=#AD18C7>14e5aa14f20345cbaf020e9b8562cbd6</font>
- `X-API-Timestamp`： 2019-12-30T15:52:41.788
- `X-API-Nonce`：3c72aa1b1d0b486b4bcd9350e9410ad5
- `X-API-Signature-Params`：top,coin_code,price_coin_code
- `X-API-Signature`： ab8c4d4535cf8d33283462d6c8571b8ca4241b608fc77659a1be2d6dae9709b2
- `Authorization`：Bearer <font color="#0BA118">b868dd65-84da-43f5-b2ca-d3dcc843e795</font>



## Preview

### RESTful API

URL: https://api.hubi.com

| Request                                  | Description                    |
| ------------------------------------------------------------ | ---------------------------------- |
| [`POST /api/asset/user/customer_asset_info/additional`](#asset) | Query assets             |
| [`GET /api/coin/coin_base_info/simple`](#coin)               | Get symbol info |
| [`GET /api/coin/coin_pairs_param/pairses`](#symbol)          | Get trading symbol info |
| [`POST /api/engine/entrust/fix`](#fix)                              | Place limit order    |
| [`PUT /api/engine/entrust/cancel/v1`](#revocation)                            | cancel order             |
| [`POST /api/engine/entrust/cancel/batch/v1`](#revocation_batch)                            | cancel order in batch |
| [`POST /api/entrust/current/top`](#current) | Query current entrust |
| [`POST /api/entrust/exchange/slice/history/user`](#history)        | Query entrust history |
| [`GET /api/entrust/info`](#entrust_info) | Get entrust info by  No. |
| [`GET /api/entrust/order/info`](#order_info) | Get order list by  No. |
| [`GET /api/connect/public/market/depth/{symbol}`](#market_depth) | Get market depth list |
| [`GET /api/connect/public/market/trade/{symbol}`](#market_trade) | Get the last trade list |

### WebSocket

URL: wss://api.hubi.com/ws/connect/v1

 		`wss://api.hubi.com/was`（ <font color="#F70000">**Deprecated**</font>）

| Topic                         | Push Method |
| ----------------------------- | ----------- |
| [`depth`](#depth)             | full        |
| [`history`](#trade)           | increment   |
| [`24H Stats`](#statistics_24) | increment   |


## Reference


### <span id="coin">Get all Supported Currencies</span>

- Request

```http
GET /api/coin/coin_base_info/simple
```
- Request Parameter

  （无）
  
- Response

```json
[
    { "id": "7KG95RD10U1KHDDCE4U8", "coinCode": "BTC" },
    { "id": "7KG95RD10U1KHDDCE4U9", "coinCode": "USDT" },
]
```


- Response Content

| Field    | Data Type | Description |
| -------- | --------- | ----------- |
| id       | String    |             |
| coinCode | String    | Currency    |



### <span id="symbol">Get all Supported Trading Symbol</span>

- Request

```http
GET /api/coin/coin_pairs_param/pairses
```

- Request Parameter

  （无）

- Response

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

- Response content (<font color="#F70000">**Array**</font>)

| Field            | Type | Description  |
| -------------------- | ------- | ---------------- |
| coinCode         | String  | Base currency |
| coinParisStr         | String  | Trading Symbol |
| coinPairs            | Array |                  |
| coinPairs[0].coinCode | String | Base currency in a trading symbol |
| coinPairs[0].priceCoinCode     | String | Quote currency in a trading symbol |
| coinPairs[0].buyFeeRate        | number  |  |
| coinPairs[0].sellFeeRate       | number  |  |
| coinPairs[0].minOrderLimit     | number  | Minimum limit order amount |
| coinPairs[0].maxOrderLimit     | number  | Max limit order amount |
| coinPairs[0].pairsPricePrecision | number  | Precision of value in quote currency |
| coinPairs[0].pairsVolumePrecision | number  | Base currency precision when quote amount(decimal places) |
| coinPairs[0].marketBuyMin      | number  | Minimum market buy order amount |
| coinPairs[0].marketBuyMax      | number  | Max market buy order amount |
| coinPairs[0].marketSellMin     | number  | Minimum market sell order amount |
| coinPairs[0].marketSellMax     | number  | Max market sell order amount |
| coinPairs[0].tradeStatus       | boolean | status       |
| coinPairs[0].openPrice         | number  | Opening price |
| coinPairs[0].marketRegion      | String  | Trading section, possible values: [main，innovation] |


### <span id="asset">Get all Accounts of the Current User</span>

- Request

```http
POST /api/asset/user/customer_asset_info/additional

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

- Request Parameter

| Field       | Type | Required | Default | Description |
| --------------- | ------ | -------- | -------------------- | -------------------- |
| page   | Number | false | 0 | Page Number |
| size   | Number | false | 10 | Maximum number of items in each response |

- Response

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

- Response Content

| Field         | Type | Description        |
| ----------------- | ------ | ---------------------- |
| size | int | Maximum number of items in each response |
| number | int |  |
| numberOfElements | int | The length of this content |
| content | Array |  |
| content[0].coinCode | String | The currency of this Account |
| content[0].availableAmount | Number   | Available Amount                               |
| content[0].frozenAmount | Number   | Frozen Amount                                  |
| content[0].lockedAmount | Number   | Locked Amount |
| content[0].totalAmount | Number   | (Available Amount+Frozen Amount+Locked Amount) |


## Trading

### <span id="fix">Place a New Order</span>

- Request

```http
POST /api/engine/entrust/fix

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

- Request Parameter

| Field       | Type | Required | Default | Description        |
| --------------- | ------ | ---------------------- | -------------------- | -------------------- |
| coin_code       | String | true | NA | Base currency in a trading symbol |
| price_coin_code | String | true | NA | Quote currency in a trading symbol |
| entrust_price   | Number | true | NA | The limit price of limit order |
| entrust_count   | Number | true | NA   | order size     |
| direction       | String | true | NA | `BUY` or`SELL` |
| trade_password  | String | false | NA | account password |

- Response

```json
{ 
    "entrustNumber": "139669789713891328", 
    "entrustMillisTime": 1579571872807 
}
```

- Response Content

| Field             | Type   | Description                                  |
| ----------------- | ------ | -------------------------------------------- |
| entrustNumber     | String | The unique order number of this order        |
| entrustMillisTime | long   | The order accepted timestamp in milliseconds |

- Error Code

| message                        | Description                                                  |
| ------------------------------ | ------------------------------------------------------------ |
| entrust_price_illegal          | Commission price cannot be empty and must be greater than 0  |
| entrust_price_scala_too_large  | Commission price with too many decimal places                |
| entrust_count_illegal          | The number of orders cannot be empty and must be greater than 0 |
| entrust_amount_scala_too_large | Too many decimal places for commission                       |
| entrust_exchange_trade_close   | Exchange Closed Trading / Alliance Closed Trading            |
| entrust_price_scala_mismatch   | Order price decimal place does not match the background configuration |
| entrust_amount_scala_mismatch  | The number of decimal places of the commission does not match the background configuration |
| entrust_amount_gt_max_allow    | The amount of orders placed is greater than the maximum trading volume |
| entrust_amount_lt_min_allow    | The order amount is smaller than the minimum transaction amount |
| entrust_trade_deny             | Users are prohibited from trading                            |
| entrust_fund_password_error    | User funds password error                                    |
| INVALID_PRICE_RANGE            | Price exceeds upper or lower limit                           |
| NOT_ACCEPTED                   | Matchmaking close                                            |
| fund_not_enough                | Insufficient funds                                           |



### <span id="revocation">Submit Cancel for an Order</span>

- Request

```http
PUT /api/engine/entrust/cancel/v1

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

- Request Parameter


| Field           | Type   | Required | Default | Description      |
| --------------- | ------ | -------- | ------- | ---------------- |
| entrust_numbers | String | true     | NA      | The Order Number |


- Response
```json
{
    "entrustNum": "139669789713891328",
    "message": "ACCEPTED"
}
```

- Response Content

| Field      | Type   | Description                           |
| ---------- | ------ | ------------------------------------- |
| entrustNum | String | The unique order number of this order |
| message    | String | The result message                    |

- Response Message

| message  | Description                                     |
| -------- | ----------------------------------------------- |
| ACCEPTED | Order Accepted                                  |
| CANCELED | Order has been revoked                          |
| FILLED   | Order has completed                             |
| FAIL     | Order was previously marked as failed to revoke |

- Error Code
| message                      | Description                     |
| ---------------------------- | ------------------------------- |
| entrust_trade_deny           | User is prohibited from trading |
| entrust_exchange_trade_close | Exchange Closes Trading         |



### <span id="revocation_batch">Submit Cancel for Multiple Orders </span>

- Request

```http
POST /api/engine/entrust/cancel/batch/v1

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```

- Request Parameter

| Field           | Type   | Required | Default | Description                           |
| --------------- | ------ | -------- | ------- | ------------------------------------- |
| entrust_numbers | String | true     | NA      | The unique order number of this order |

- Response

```json
[
    {
        "entrustNum": "139669789713891328",
        "message": "CANCELED"
    }
]

```

- Response Content

| Field      | Type   | Description                           |
| ---------- | ------ | ------------------------------------- |
| entrustNum | String | The unique order number of this order |
| message    | String | success or error code                 |

- message

| message  | Description          |
| -------- | -------------------- |
| ACCEPTED | Accepted the request |
| CANCELED | Order had canceled   |
| FILLED   | Order had Completed  |
| FAIL     | Cancel Fail          |
|          |                      |

- Error Code

| message                      | Description                  |
| ---------------------------- | ---------------------------- |
| entrust_trade_deny           | Current User Disable Trading |
| entrust_exchange_trade_close | Closed Trading               |




### <span id="current">Get Latest Open Orders</span>

-  Request

```http
POST /api/entrust/current/top

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```


- Request Parameter


| Field           | Type   | Required | Default | Description                        |
| --------------- | ------ | -------- | ------- | ---------------------------------- |
| top             | int    | false    | 100     | max value 500                      |
| coin_code       | String | true     |         | Base currency in a trading symbol  |
| price_coin_code | String | true     |         | Quote currency in a trading symbol |


- Response

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

- Response <font color="#F70000">**Array**</font> Content

| Field            | Type   | Description                                                 |
| ---------------- | ------ | ----------------------------------------------------------- |
| no               | String | The unique order number of this order                       |
| coinCode         | String | Base currency in a trading symbol                           |
| priceCoinCode    | String | Quote currency in a trading symbol                          |
| way              | String | The order type                                              |
| direction        | String | Buy or Sell                                                 |
| price            | Number | The limit price of limit order, only needed for limit order |
| count            | Number | order size                                                  |
| millisTime       | long   | order timestamp                                             |
| surplusCount     | Number | left                                                        |
| transactionCount | Number | The amount which has been filled                            |
| canceledAmount   | Number | The amount of cancel request sent successfully              |
| status           | String | Status                                                      |

- Status Values

| 状态             | Description |
| ---------------- | ----------- |
| SUBMITTED        |             |
| PARTIAL_FILLED   |             |
| PARTIAL_CANCELED |             |
| FILLED           |             |
| CANCELED         |             |




### <span id="history">Search Historical Orders </span>

- Request

```http
POST /api/entrust/exchange/slice/history/user

Content-Type: application/x-www-form-urlencoded; charset=utf-8
```


- Request Parameter

| page            | int     | Required | Default | Description                              |      |
| --------------- | ------- | -------- | ------- | ---------------------------------------- | ---- |
| size            | int     | false    | 10      | Maximum number of items in each response |      |
| coin_code       | String  | true     | NA      | Base currency in a trading symbol        |      |
| price_coin_code | String  | true     | NA      | Quote currency in a trading symbol       |      |
| direction       | String  | false    | NA      | Buy or Sell                              |      |
| begin_time      | Date    | false    | NA      | format: yyyy-MM-dd HH:mm:ss              |      |
| end_time        | Date    | false    | NA      | format: yyyy-MM-dd HH:mm:ss              |      |
| filter_canceled | boolean | false    | false   | return had canceled                      |      |

- Response

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

- Response Content

| Field                     | Type   | Description                                                 |
| ------------------------- | ------ | ----------------------------------------------------------- |
| size                      | int    |                                                             |
| number                    | int    |                                                             |
| numberOfElements          | int    |                                                             |
| content[]                 | Array  |                                                             |
| content[0].entrustNumber  | String | The Order Number                                            |
| content[0].coinCode       | String | Base currency in a trading symbol                           |
| content[0].priceCoinCode  | String | Quote currency in a trading symbol                          |
| content[0].direction      | String | Buy or Sell                                                 |
| content[0].entrustPrice   | Number | The limit price of limit order, only needed for limit order |
| content[0].entrustCount   | Number | order size                                                  |
| content[0].entrustTime    | Date   | The timestamp in milliseconds when the order was created    |
| content[0].entrustWay     | String | order type                                                  |
| content[0].turnover       | Number | The amount which has been filled                            |
| content[0].processedPrice | Number | match price                                                 |
| content[0].status         | String | Order Status                                                |

  

### <span id="entrust_info">Get Order Detail by Order No.</span>

- Request

```http
GET /api/entrust/info
```

- Request Parameter

| Field          | Type   | Required | Default | Description                           |
| -------------- | ------ | -------- | ------- | ------------------------------------- |
| entrust_number | String | true     | NA      | The unique order number of this order |

- Response

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

- Response Content

| Field          | Type   | Description  |
| -------------- | ------ | ------------ |
| entrustNumber  | String | 委托单号     |
| coinCode       | String | 交易币       |
| priceCoinCode  | String | 定价币       |
| direction      | String | 买卖方向     |
| entrustPrice   | Number | 委托价       |
| entrustCount   | Number | 委托数量     |
| entrustTime    | Date   | 委托时间     |
| entrustWay     | String | 委托Type     |
| turnover       | Number | 成交数量数量 |
| processedPrice | Number | 成交均价     |
| status         | String | 委托单状态   |

  

### <span id="order_info">Get Match Result by Order No.</span>

- Request

```http
GET /api/entrust/order/info
```

- Request Parameter

| Field          | Type   | Required | Default | Description                           |
| -------------- | ------ | -------- | ------- | ------------------------------------- |
| entrust_number | String | true     | NA      | The unique order number of this order |

- Response

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

- Response Array Content

| Field              | Type   | Description                            |
| ------------------ | ------ | -------------------------------------- |
| custId             | String | Customer ID                            |
| entrustNum         | String | Order No.                              |
| orderNumber        | String | Math Order No.                         |
| coinCode           | String | Base currency in a trading symbol      |
| priceCoinCode      | String | Quote currency in a trading symbol     |
| direction          | String | Buy or Sell                            |
| entrustPrice       | Number | The limit price of limit order         |
| transactionPrice   | Number | The match price of order               |
| transactionCount   | Number | The amount which has been filled       |
| transactionFee     | Number | Transaction fee paid so far            |
| transactionFeeRate | Number | Transaction fee rate                   |
| transactionSum     | Number | The total amount which has been filled |

  

## <span id="was">Market</span>



### <span id="market_depth">Get market Depth</span>

- Request

```http
GET /api/connect/public/market/depth/{symbol}
```

- Request Parameter

| Field  | Type   | Required | Default | Description                 |
| ------ | ------ | -------- | ------- | --------------------------- |
| symbol | String | true     | NA      | The trading symbol to query |

- Response

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

- Response Content

| Field            | Type | Description |
| ------------------ | ------ | -------- |
| asks | Array | order by price desc |
| asks.price              | String | ask price |
| asks.volume             | String | total volume of current price |
| bids | Array | order by price desc |
| bids.price              | String | bid price |
| bids.volume             | String | total volume of current price |
| last_strike_price | String | last strike price |
| timestamp | long | The timestamp in milliseconds |

 

### <span id="market_trade">Get Last Trade</span>

- Request

```http
GET /api/connect/public/market/trade/{symbol}
```

- Request Parameter

| Field  | Type   | Required | Default | Description                              |
| ------ | ------ | -------- | ------- | ---------------------------------------- |
| symbol | String | true     | NA      | The trading symbol to query              |
| size   | int    | false    | 30      | Maximum number of items in each response |

- Response

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

- Response <font color="#F70000">**Array**</font> Content

| Field  | Type   | Description                         |
| ------ | ------ | ----------------------------------- |
| id     | String | The unique trade id of this trade   |
| price  | String | The trading price in quote currency |
| symbol | String | The trading symbol                  |
| time   | long   | The timestamp in milliseconds       |
| type   | String | Buy or Sell                         |
| volume | String | The trading volume in base currency |

 

##  WebSocket Market



### <span id="depth">Depth</span>

- Topic：

```json
  {
    "channel": "depth_all",
    "symbol": "btcusdt"
  }
```

- Response:

```json
ACCEPTED:{ "channel": "depth_all", "symbol": "btcusdt" }
```

- Update Content

| Field           | Type | Description |
| ----------------- | ------ | ---- |
| data | Object |  |
| data.asks          | Array | order by price desc |
| data.asks.amount     | String | link `data.asks.volume`（to be obsoleted)） |
| data.asks.price        | String | price |
| data.asks.volume       | String | quote volume |
| data.bids            | Array | order by price desc |
| data.bids.amount      | String | link `data.bids.volume`（to be obsoleted)） |
| data.bids.price        | String | price |
| data.bids.volume       | String | quote volume |
| data.last_strike_price | String | The last price |
| dataType                  | String | link  `Topic.channel` |
| symbol                  | String | link  `Topic.symbol` |
| timestamp                  | long | The timestamp in milliseconds |




### <span id="trade">Trade</span>

- Topic：

```json
{
    "channel": "trade_history",
    "symbol": "btcusdt"
}
```

- Response:

```json
ACCEPTED:{ "channel": "trade_history", "symbol": "btcusdt", "bourse": "01001" }
```

- Update Content

| Field | Type | Description |
| ------ | ------ | ---- |
| data | Object |  |
| data.curentExchangePrice  | String | last price |
| data.trades  | Array |  |
| data.trades.amount | String | The trading volume in base currency |
| data.trades.id | String | The unique trade id of this trade |
| data.trades.price | String | The trading price in quote currency |
| data.trades.symbol | String | The trading symbol |
| data.trades.time | long | The timestamp in milliseconds when match |
| data.trades.type | String | Buy or Sell |
| data.trades.volume | String | The trading volume in base currency |
| dataType | String | link `Topic.channel` |
| symbol | String | link  `Topic.symbol` |
| timestamp | long | The timestamp in milliseconds |



### <span id="statistics_24">Last 24h Market Summary</span>

- Topic

```json
{
    "channel":"statistics_24",
    "symbol":"HUB_USDT,BTC_USDT"
}
```

- Response

```json
ACCEPTED:{ "channel":"statistics_24", "symbol":"HUB_USDT,BTC_USDT,ETH_USDT,LTC_USDT,EOS_USDT,ETH_BTC" }
```
```json
{"code":406,"message":"NOT_ACCEPTABLE","sid":"7bfdceea"}
```

- Push  Content

| Field | Type | Description |
| ------ | ------ | ---- |
| data | Array |  |
| data.high   | String | High price in past 24H |
| data.low    | String | Low price in past 24H |
| data.new    | String | Last price |
| data.price  | String | Last price |
| data.rate   | String | rate=(price-open)/open |
| data.symbol | String | The trading symbol |
| data.time   | String | UNIX epoch timestamp in second |
| data.volume | String | Aggregated trading value in past 24H (in quote currency) |
| dataType | String | link `Topic.channel` |
| symbol | String | link  `Topic.symbol` |
| timestamp | long | The timestamp in milliseconds when last updated |


