---
description: 请求签名规则说明
---

# 签名

## 规则

REST API 请求在通过 internet 传输的过程中极有可能被篡改，为了提高请求的安全性，除公共接口（行情数据）外的私有接口均必须使用 `API Key` 做签名处理，以校验参数和参数值在传输途中是否发生了更改。

一个合法的请求头部需包含以下信息：

* `X-API-Version`： 请求的API版本，现在只支持1.0.0。
* `X-API-Key`： Access Key，您申请的 API Key中的Access Key。
* `X-API-Timestamp`： 请求时间戳，ISO格式，如：2018-07-18T01:25:47.048Z，字符串。
* `X-API-Nonce`：通过md5\(client\_id + timestamp + seqNum\)计算行到，client\_id是申请的Access Key， timestamp是请求时间戳，seqNum是一串随机数。
* `X-API-Signature-Params`：需要签名的参数，使用英文逗号分隔，如 top,coin\_code,price\_coin\_code
* `X-API-Signature`： 签名结果，通过HmacSHA256\(client\_secret, params + version + nonce + url\) 得到，client\_secret是您申请的API Key中的Secret Key，params是参与签名的参数键值对，通过"键=值&"来连接，如 top=100&coin\_code=HUB&price\_coin\_code=USDT，按照`X-API-Signature-Params`指定的参数顺序排序，version=1.0.0，字段连接时不需要加号（+）详见示例。
* `Authorization`： Bearer access\_token， Bearer 固定的，access\_token是您申请的API Key中的 access token。

{% hint style="info" %}
GET, PUT, POST 请求的参数都需要参与签名运算
{% endhint %}

## 示例

以下面的信息为例

```text
URL：POST /api/entrust/current/top
参数：top=100,coin_code=HUB,price_coin_code=USDT
Access Key = 14e5aa14f20345cbaf020e9b8562cbd6
Secret Key = b3a0a2a36d0f4b52b697ac2df3484bc2
Access Token = b868dd65-84da-43f5-b2ca-d3dcc843e795
```

**步骤**

1. 设置随机数 seqNum=999 （建议用自增序列，每请求一次增加1）
2. 获取当前时间得到 timestamp=2019-12-30T15:52:41.788
3. 计算MD5\(14e5aa14f20345cbaf020e9b8562cbd62019-12-30T15:52:41.788999\)，得到nonce = 3c72aa1b1d0b486b4bcd9350e9410ad5
4. HmacSHA256签名，HmacSHA256\(b3a0a2a36d0f4b52b697ac2df3484bc2, top=100&coin\_code=HUB&price\_coin\_code=USDT1.0.03c72aa1b1d0b486b4bcd9350e9410ad5/api/entrust/current/top\)，得到signature = ab8c4d4535cf8d33283462d6c8571b8ca4241b608fc77659a1be2d6dae9709b2

{% hint style="info" %}
_MD5, SHA256 计算时字符集使用 UTF-8_
{% endhint %}

最后得到 header

```text
X-API-Version： 1.0.0
X-API-Key： 14e5aa14f20345cbaf020e9b8562cbd6
X-API-Timestamp： 2019-12-30T15:52:41.788
X-API-Nonce：3c72aa1b1d0b486b4bcd9350e9410ad5
X-API-Signature-Params：top,coin_code,price_coin_code
X-API-Signature： ab8c4d4535cf8d33283462d6c8571b8ca4241b608fc77659a1be2d6dae9709b2
Authorization：Bearer b868dd65-84da-43f5-b2ca-d3dcc843e795
```

