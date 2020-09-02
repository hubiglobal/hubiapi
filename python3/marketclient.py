#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# pip3 install requests

import datetime
import hashlib
import hmac
import random
import urllib.parse
import requests
import logging

DEFAULT_CHARSET = 'utf-8'
BASE_URL = 'https://api.hubi.pub'
KEY = "***"
SECRET = "***"
ACCESS_TOKEN = "***"
HMAC_KEY = SECRET.encode(DEFAULT_CHARSET)


def sign(path: str, params: dict = {}):
    version = '1.0.0'
    timestamp = datetime.datetime.utcnow().isoformat(timespec='milliseconds') + 'Z'
    nonce = f'{KEY}{timestamp }{random.random()}'
    nonceHash = hashlib.md5(nonce.encode(DEFAULT_CHARSET)).hexdigest().lower()

    paramsMapString = urllib.parse.urlencode(params, safe=',')
    content = f'{paramsMapString}{version}{nonceHash}{path}'
    sign = hmac.new(key=HMAC_KEY, msg=content.encode(
        DEFAULT_CHARSET), digestmod=hashlib.sha256).hexdigest().lower()

    print("content: ", content)
    print("sign: ", sign)

    headers = {}
    headers['Authorization'] = 'Bearer ' + ACCESS_TOKEN
    headers['X-API-Key'] = KEY
    headers['X-API-Version'] = version
    headers['X-API-Timestamp'] = timestamp
    headers['X-API-Nonce'] = nonceHash
    headers['X-API-Signature-Params'] = ','.join(params.keys())
    headers['X-API-Signature'] = sign

    return headers


def get(path: str, params: dict = {}):
    headers = sign(path, params)
    return requests.get(BASE_URL+path, params=params, headers=headers)


def post(path: str, params: dict = {}):
    headers = sign(path, params)
    headers['Content-type'] = 'application/x-www-form-urlencoded'
    return requests.post(BASE_URL + path, data=params, headers=headers)


def ref_data():
    r"""获取合约信息

    :return
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
        {
          "symbol": "XLTCUSD",
          "tick": 0.01,
          "lotSize": 1.0,
          "type": "PERP"
        },
        {
          "symbol": "XBCHUSD",
          "tick": 0.05,
          "lotSize": 1.0,
          "type": "PERP"
        },
        {
          "symbol": "XLINKUSD",
          "tick": 0.005,
          "lotSize": 1.0,
          "type": "PERP"
        },
        {
          "symbol": "XDOTUSD",
          "tick": 0.005,
          "lotSize": 1.0,
          "type": "PERP"
        },
        {
          "symbol": "XXTZUSD",
          "tick": 0.001,
          "lotSize": 1.0,
          "type": "PERP"
        },
        {
          "symbol": "XCOMPUSD",
          "tick": 0.05,
          "lotSize": 1.0,
          "type": "PERP"
        },
        {
          "symbol": "XBANDUSD",
          "tick": 0.005,
          "lotSize": 1.0,
          "type": "PERP"
        }
      ]
    }

    """
    path = '/api/futures/public/basic/refData'
    response = get(path)
    print(response.text)
    return response


def last_price():
    r"""获取合约最新成交价

    :return
    {
       "code": 0,
       "message": "OK",
       "result": {
         "XETHUSD": 222.65,
         "XBCHUSD": 219.5,
         "XEOSUSD": 2.341,
         "XLTCUSD": 40.89,
         "XBTCUSD": 9084
       }
    } 
    """
    path = '/api/futures/public/basic/lastPrice'
    response = get(path, params)
    print(response.text)
    return response


def price(symbols: str):
    r"""获取标记价
    请求频率限定：5次/秒/IP。

    :param symbols 标记名称，多个标记以逗号分隔，eg: BTCUSD,ETHUSD （注意：和合约名称进行区分，标记名前去掉X）
    :return
    {
     "code": 0,
     "message": "OK",
     "result": [
       {
         "symbol": "BTCUSD",
         "source": "INDEX",
         "updatedTime": "2020-12-30T12:04:56.859+0000",
         "value": 12063.67525,
         "qty": 0
       }
     ]
    }
    """
    path = '/api/futures/public/index/price'
    params = {'symbols': symbols}
    response = get(path, params)
    print(response.text)
    return response


def depth(symbol: str):
    r"""获取市场深度

    :param symbol 合约名称，eg: XBTCUSD。
    :return

    """
    path = '/api/futures/public/depth/depth'
    params = {'symbol': symbol}
    response = get(path, params)
    print(response.text)
    return response


def trades(symbol: str, sequence: str):
    r"""获取最新成交记录; 5次/秒/IP

    :param symbol 合约名称，eg: XBTCUSD
    :param sequence 上一次获取成交记录的最新一笔记录的 id；第一次请求传入空值，
    :result 返回最近50笔成交。
    """
    path = '/api/futures/public/depth/trades'
    params = {'symbol': symbol, 'sequence': sequence}
    response = get(path, params)
    print(response.text)
    return response


def kline_latest(symbol: str, type: str):
    r"""获取最新一根 K 线数据。
    请求频次限定：75次/分/IP
    """

    path = '/api/futures/public/kLine/latest'
    params = {'symbol': symbol, 'type': type}
    response = get(path, params)
    print(response.text)
    return response


def fundingRate(symbols: str):
    r"""获取资金费率。
    请求频次限定：5次/秒/IP。

    :param symbols 合约名称，多个合约以逗号分隔，eg: XBTCUSD,XETHUSD
    """

    path = '/api/futures/public/kLine/fundingRate'
    params = {'symbols': symbols}
    response = get(path, params)
    print(response.text)
    return response


def trade_stats(symbols: str):
    r"""获取最近24小时的成交统计数据。
    请求频次限定：5次/秒/IP

    :param symbols 合约名称，多个合约以逗号分隔，eg: XBTCUSD,XETHUSD
    """

    path = '/api/futures/public/kLine/tradeStatistics'
    params = {'symbols': symbols}
    response = get(path, params)
    print(response.text)
    return response


def open_interest(symbol: str):
    r"""获取系统合约持仓量。
    请求频次限定：5次/秒/IP

    :param symbol 合约名称，eg: BTCUSD （注意：和合约名称进行区分，标记名前去掉X）
    """

    path = '/api/futures/public/kLine/openInterest'
    params = {'symbol': symbol}
    response = get(path, params)
    print(response.text)
    return response


if __name__ == '__main__':
    ref_data()
    # price('BTCUSD,ETHUSD')
    # depth('XBTCUSD')
    # trades('XBTCUSD', '')
    # kline_latest('XBTCUSD', '5M')
    # fundingRate('XBTCUSD,XETHUSD')
    # trade_stats('XBTCUSD,XETHUSD')
    # trade_stats('XBTCUSD,XETHUSD')
    # open_interest('BTCUSD')
