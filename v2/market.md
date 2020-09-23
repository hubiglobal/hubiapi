# 行情 REST API

{% api-method method="get" host="https://api.hubi.com" path="/api/futures/public/ref\_data" %}
{% api-method-summary %}
获取币种
{% endapi-method-summary %}

{% api-method-description %}
This endpoint allows you to get free cakes.
{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}
Cake successfully retrieved.
{% endapi-method-response-example-description %}

```
[
  {
    "lotSize": 1.0,
    "symbol": "BTCUSD",
    "tick": 1.0,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "ETHUSD",
    "tick": 0.05,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "EOSUSD",
    "tick": 0.001,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "LTCUSD",
    "tick": 0.01,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "BCHUSD",
    "tick": 0.05,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": null,
    "tick": 2.0E-5,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "LINKUSD",
    "tick": 0.005,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "DOTUSD",
    "tick": 0.005,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": null,
    "tick": 0.005,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "XTZUSD",
    "tick": 0.001,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "COMPUSD",
    "tick": 0.05,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "BANDUSD",
    "tick": 0.005,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": null,
    "tick": 10.0,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": null,
    "tick": 0.005,
    "type": "PERP"
  }
]
```
{% endapi-method-response-example %}

{% api-method-response-example httpCode=500 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
{
    "message": "error_message"
}
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

{% api-method method="get" host="https://api.hubi.com" path="/api/futures/public/last\_price" %}
{% api-method-summary %}
获取最新成交价
{% endapi-method-summary %}

{% api-method-description %}
This endpoint allows you to get free cakes.
{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}
Cake successfully retrieved.
{% endapi-method-response-example-description %}

```
{
  "BANDUSD": 6.32,
  "BCHUSD": 214.85,
  "BTCUSD": 10479.0,
  "COMPUSD": 139.5,
  "CRVUSD": 1.035,
  "DOTUSD": 4.185,
  "EOSUSD": 2.55,
  "ETHUSD": 339.3,
  "LINKUSD": 8.215,
  "LTCUSD": 44.45,
  "TRXUSD": 0.02578,
  "UNIUSD": 4.55,
  "XTZUSD": 2.026,
  "YFIUSD": 25120.0
}
```
{% endapi-method-response-example %}

{% api-method-response-example httpCode=500 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
{
    "message": "error_message"
}
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

{% api-method method="get" host="https://api.hubi.com" path="/api/futures/public/depth/depth" %}
{% api-method-summary %}
获取市场深度
{% endapi-method-summary %}

{% api-method-description %}
This endpoint allows you to get free cakes.
{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-query-parameters %}
{% api-method-parameter name="symbol" type="string" required=true %}
合约名称，eg: BTCUSD
{% endapi-method-parameter %}
{% endapi-method-query-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}
Cake successfully retrieved.
{% endapi-method-response-example-description %}

```
{
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
```
{% endapi-method-response-example %}

{% api-method-response-example httpCode=500 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
{
    "message": "error_message"
}
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

{% api-method method="get" host="https://api.hubi.com" path="/api/futures/public/depth/trades" %}
{% api-method-summary %}
获取最新成交记录
{% endapi-method-summary %}

{% api-method-description %}
This endpoint allows you to get free cakes.
{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-query-parameters %}
{% api-method-parameter name="symbol" type="string" required=true %}
合约名称，eg: BTCUSD
{% endapi-method-parameter %}

{% api-method-parameter name="sequence" type="string" required=false %}
上一次获取成交记录的最新一笔记录的 id；第一次请求传入空字符串
{% endapi-method-parameter %}
{% endapi-method-query-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}
Cake successfully retrieved.
{% endapi-method-response-example-description %}

```
[
  {
    "id": "1599127941729000003",
    "symbol": "BTCUSD",
    "price": 11335.0,
    "qty": 23608.0,
    "buyActive": false,
    "timestamp": "2020-09-03T10:12:21.730+0000"
  },
  {
    "id": "1599127940647000003",
    "symbol": "BTCUSD",
    "price": 11335.0,
    "qty": 5220.0,
    "buyActive": true,
    "timestamp": "2020-09-03T10:12:20.648+0000"
  },
  {
    "id": "1599127938648000003",
    "symbol": "BTCUSD",
    "price": 11336.0,
    "qty": 14509.0,
    "buyActive": false,
    "timestamp": "2020-09-03T10:12:18.649+0000"
  },
  {
    "id": "1599127933929000003",
    "symbol": "BTCUSD",
    "price": 11337.0,
    "qty": 10422.0,
    "buyActive": true,
    "timestamp": "2020-09-03T10:12:13.930+0000"
  },
  {
    "id": "1599127931819000003",
    "symbol": "BTCUSD",
    "price": 11335.0,
    "qty": 1000.0,
    "buyActive": false,
    "timestamp": "2020-09-03T10:12:11.820+0000"
  },
  {
    "id": "1599127930305000053",
    "symbol": "BTCUSD",
    "price": 11336.0,
    "qty": 500.0,
    "buyActive": false,
    "timestamp": "2020-09-03T10:12:10.309+0000"
  }
]
```
{% endapi-method-response-example %}

{% api-method-response-example httpCode=500 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
{
    "message": "error_message"
}
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}



{% api-method method="get" host="https://api.hubi.com" path="/api/futures/public/kline/by\_index" %}
{% api-method-summary %}
通过下标索引获取历史 K 线
{% endapi-method-summary %}

{% api-method-description %}
This endpoint allows you to get free cakes.
{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-query-parameters %}
{% api-method-parameter name="symbol" type="string" required=true %}
合约名称，eg: BTCUSD
{% endapi-method-parameter %}

{% api-method-parameter name="type" type="string" required=true %}
K线类型,eg: 5M 15M 1H
{% endapi-method-parameter %}

{% api-method-parameter name="step" type="integer" required=true %}
返回的数据量，eg:10
{% endapi-method-parameter %}

{% api-method-parameter name="from" type="integer" required=false %}
起始时间戳
{% endapi-method-parameter %}
{% endapi-method-query-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}
Cake successfully retrieved.
{% endapi-method-response-example-description %}

```
[
  {
    "lotSize": 1.0,
    "symbol": "BTCUSD",
    "tick": 1.0,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "ETHUSD",
    "tick": 0.05,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "EOSUSD",
    "tick": 0.001,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "LTCUSD",
    "tick": 0.01,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "BCHUSD",
    "tick": 0.05,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": null,
    "tick": 2.0E-5,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "LINKUSD",
    "tick": 0.005,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "DOTUSD",
    "tick": 0.005,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": null,
    "tick": 0.005,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "XTZUSD",
    "tick": 0.001,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "COMPUSD",
    "tick": 0.05,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "BANDUSD",
    "tick": 0.005,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": null,
    "tick": 10.0,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": null,
    "tick": 0.005,
    "type": "PERP"
  }
]
```
{% endapi-method-response-example %}

{% api-method-response-example httpCode=500 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
{
    "message": "error_message"
}
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

{% api-method method="get" host="https://api.hubi.com" path="/api/futures/public/kline/by\_time" %}
{% api-method-summary %}
通过时间索引获取历史 K 线
{% endapi-method-summary %}

{% api-method-description %}
This endpoint allows you to get free cakes.
{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-query-parameters %}
{% api-method-parameter name="symbol" type="string" required=true %}
合约名称，eg: BTCUSD
{% endapi-method-parameter %}

{% api-method-parameter name="type" type="string" required=true %}
K线类型,eg: 5M 15M 1H
{% endapi-method-parameter %}

{% api-method-parameter name="step" type="integer" required=true %}
返回的数据量，eg:10
{% endapi-method-parameter %}

{% api-method-parameter name="from" type="integer" required=false %}
起始时间戳
{% endapi-method-parameter %}
{% endapi-method-query-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}
Cake successfully retrieved.
{% endapi-method-response-example-description %}

```
[
  {
    "lotSize": 1.0,
    "symbol": "BTCUSD",
    "tick": 1.0,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "ETHUSD",
    "tick": 0.05,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "EOSUSD",
    "tick": 0.001,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "LTCUSD",
    "tick": 0.01,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "BCHUSD",
    "tick": 0.05,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": null,
    "tick": 2.0E-5,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "LINKUSD",
    "tick": 0.005,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "DOTUSD",
    "tick": 0.005,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": null,
    "tick": 0.005,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "XTZUSD",
    "tick": 0.001,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "COMPUSD",
    "tick": 0.05,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": "BANDUSD",
    "tick": 0.005,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": null,
    "tick": 10.0,
    "type": "PERP"
  },
  {
    "lotSize": 1.0,
    "symbol": null,
    "tick": 0.005,
    "type": "PERP"
  }
]
```
{% endapi-method-response-example %}

{% api-method-response-example httpCode=500 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
{
    "message": "error_message"
}
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

{% api-method method="get" host="https://api.hubi.com" path="/api/futures/public/kLine/latest" %}
{% api-method-summary %}
获取最新一根 K 线数据
{% endapi-method-summary %}

{% api-method-description %}
This endpoint allows you to get free cakes.
{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-query-parameters %}
{% api-method-parameter name="symbol" type="string" required=true %}
合约名称，eg: BTCUSD
{% endapi-method-parameter %}

{% api-method-parameter name="type" type="string" required=true %}
K线类型,eg: 5M 15M 1H
{% endapi-method-parameter %}
{% endapi-method-query-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}
Cake successfully retrieved.
{% endapi-method-response-example-description %}

```
{
  "source": null,
  "symbol": "BTCUSD",
  "open": 11331.0,
  "close": 11336.0,
  "high": 11337.0,
  "low": 11330.0,
  "keyTime": "2020-09-03T10:15:00.000+0000",
  "timeStamp": "2020-09-03T10:16:21.973+0000",
  "volume": 319767.0,
  "turnover": 28.212856174373286
}
```
{% endapi-method-response-example %}

{% api-method-response-example httpCode=500 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
{
    "message": "error_message"
}
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

{% api-method method="get" host="https://api.hubi.com" path="/api/futures/public/kLine/funding\_rate" %}
{% api-method-summary %}
获取资金费率
{% endapi-method-summary %}

{% api-method-description %}
This endpoint allows you to get free cakes.
{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-query-parameters %}
{% api-method-parameter name="symbols" type="string" required=true %}
合约名称，多个合约以英文逗号分隔，eg: BTCUSD,ETHUSD
{% endapi-method-parameter %}
{% endapi-method-query-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}
Cake successfully retrieved.
{% endapi-method-response-example-description %}

```
[
  {
    "date": "2020-09-23T20:00:00.000+0000",
    "rate": -0.000265,
    "symbol": "BTCUSD"
  },
  {
    "date": "2020-09-23T20:00:00.000+0000",
    "rate": -0.00108,
    "symbol": "ETHUSD"
  }
]
```
{% endapi-method-response-example %}

{% api-method-response-example httpCode=500 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
{
    "message": "error_message"
}
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

{% api-method method="get" host="https://api.hubi.com" path="/api/futures/public/kLine/trade\_statistics" %}
{% api-method-summary %}
获取最近24小时的成交统计数据
{% endapi-method-summary %}

{% api-method-description %}
This endpoint allows you to get free cakes.
{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-query-parameters %}
{% api-method-parameter name="symbols" type="string" required=true %}
合约名称，多个合约以逗号分隔，eg: BTCUSD,ETHUSD
{% endapi-method-parameter %}
{% endapi-method-query-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}
Cake successfully retrieved.
{% endapi-method-response-example-description %}

```
[
  {
    "symbol": "BTCUSD",
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
    "symbol": "ETHUSD",
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
```
{% endapi-method-response-example %}

{% api-method-response-example httpCode=500 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
{
    "message": "error_message"
}
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

{% api-method method="get" host="https://api.hubi.com" path="/api/futures/public/kline/open\_interest" %}
{% api-method-summary %}
获取系统合约持仓量
{% endapi-method-summary %}

{% api-method-description %}
This endpoint allows you to get free cakes.
{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-query-parameters %}
{% api-method-parameter name="symbol" type="string" required=true %}
合约名称，eg: BTCUSD
{% endapi-method-parameter %}
{% endapi-method-query-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}
Cake successfully retrieved.
{% endapi-method-response-example-description %}

```
{
  "symbol": "BTCUSD",
  "value": 31865.068938608492,
  "date": "2020-09-03T10:21:27.659+0000",
  "qty": 3.60361268E8
}
```
{% endapi-method-response-example %}

{% api-method-response-example httpCode=500 %}
{% api-method-response-example-description %}

{% endapi-method-response-example-description %}

```
{
    "message": "error_message"
}
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

