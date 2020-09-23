# 行情 WebSocket API

{% hint style="info" %}
### 链接 <a id="lian-jie-2"></a>

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



### 

