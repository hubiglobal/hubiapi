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


def list_symbol():
    r"""合约列表

    """
    path = '/api/futures/public/list_symbol'
    response = get(path)
    print(response.text)
    return response


def query_active_orders(symbol: str):
    r"""查询用户未完成的订单

    :param symbol 合约， BTCUSD, BCHUSD...
    """
    path = '/api/futures/query_active_orders'
    params = {'symbol': symbol}
    response = get(path, params)
    print(response.text)
    return response


def query_orders_pro(timestamp):
    r"""查询用户的某个订单

    :param timestamp 上次请求的时间毫秒数
    """
    path = '/api/futures/query_orders_pro'
    params = {"timestamp": timestamp}
    response = get(path, params)
    print(response.text)
    return response


def query_order(order_no: str):
    r"""查询用户的某个订单

    :param order_no 订单号
    """
    path = '/api/futures/query_order_by_id'
    params = {"order_no": order_no}
    response = get(path, params)
    print(response.text)
    return response


def query_accounts():
    r"""查询合约账户
    """
    path = '/api/futures/query_accounts'
    response = get(path)
    print(response.text)
    return response


def query_position():
    r"""查询仓位
    """
    path = '/api/futures/query_position'
    response = get(path)
    print(response.text)
    return response


def enter_order(coin_code: str, symbol: str, open_position: bool, quantity: str, price: str, trade_direction: str, order_type: str):
    r"""下单
    :param coin_code: 结算币种
    :param symbol: 合约， BTCUSD, BCHUSD
    :param open_position: 开平仓， 开仓：true, 平仓：false
    :param quantity: 数量
    :param price: 价格
    :param trade_direction: BUY, SELL
    :param order_type: 限价: LIMIT，市价: MARKET
    :return 返回订单号
    """
    path = '/api/futures/enter_order'
    params = {'coin_code': coin_code,
              'symbol': symbol,
              'open_position': open_position,
              'quantity': quantity,
              'price': price,
              'trade_direction': trade_direction,
              'order_type': order_type
              }
    response = post(path, params)
    print(response.text)
    return response


def cancel_order(order_no: str):
    r"""取消订单

    :param order_no: 订单号
    """
    path = '/api/futures/cancel_order'
    params = {'order_no': order_no}
    response = post(path, params)
    print(response.text)
    return response


def cancel_order_batch(order_nos: str):
    r"""批量取消订单

    :param order_nos: 订单号，多个用逗号分隔
    """
    path = '/api/futures/cancel_order_batch'
    params = {'order_nos': order_nos}
    response = post(path, params)
    print(response.text)
    return response


if __name__ == '__main__':
    # list_symbol()
    # query_active_orders('ETHUSD')
    # query_accounts()
    # query_position()
    # enter_order('USDT', 'ETHUSD', 'true', '100', '465', 'BUY', 'LIMIT')
    # query_order('O150-20200901-140743-073-0045')
