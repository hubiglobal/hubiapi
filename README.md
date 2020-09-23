# 开始

{% hint style="info" %}
Hubi提供的接口包括公共接口和私有接口两种类型。

公共接口可用于获取行情数据，无需认证即可调用。

私有接口可用于获取基础数据、交易管理，每个私有请求必须使用 `API Key` 进行[签名](sign.md)。
{% endhint %}

## 准备接入

您需要先登录[网页端](https://www.hubi.pub/zh/api/setting)申请API key，必要的话设置访问白名单（CIDR），默认不限制。

创建成功后请务必记住以下信息：

* `API Key` API 访问唯一标识
* `Secret Key` 签名认证加密所使用的密钥（仅申请时可见）
* `Access Token` 用户身份标识（仅申请时可见）

## [REST API 签名](sign.md)

## 合约

{% tabs %}
{% tab title="JAVA" %}
{% embed url="https://github.com/hubiglobal/hubiapi/blob/master/java" %}
{% endtab %}

{% tab title="Python" %}
{% embed url="https://github.com/hubiglobal/hubiapi/blob/master/python3/" %}
{% endtab %}
{% endtabs %}

{% page-ref page="v2/trade-rest-api.md" %}

{% page-ref page="v2/market.md" %}

{% page-ref page="v2/market-websocket-api.md" %}

## 币币

{% tabs %}
{% tab title="JAVA" %}
{% embed url="https://github.com/hubiglobal/hubiapi/blob/master/java" %}
{% endtab %}

{% tab title="Python" %}
{% embed url="https://github.com/hubiglobal/hubiapi/blob/master/python3" %}
{% endtab %}
{% endtabs %}

{% page-ref page="bi-bi/trade.md" %}

{% page-ref page="bi-bi/market.md" %}



