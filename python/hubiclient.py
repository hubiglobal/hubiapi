#!/usr/bin/env python
# -*- coding: utf-8 -*-

# pip install requests

import datetime
import hashlib
import hmac
import random
import urllib.parse
import requests

DEFAULT_CHARSET = 'utf-8'
BASE_URL = 'https://api.hubi.com'
KEY = "***"
SECRET = "***"
ACCESS_TOKEN = "***"
HMAC_KEY = SECRET.encode(DEFAULT_CHARSET)


def sign(path: str, params: dict = {}):
    version = '1.0.0'
    timestamp = datetime.datetime.utcnow().isoformat(timespec='milliseconds') + 'Z'
    nonce = f'{KEY}{timestamp }{random.random()}'
    nonceHash = hashlib.md5(nonce.encode(DEFAULT_CHARSET)).hexdigest().lower()

    paramsMapString = urllib.parse.urlencode(params)
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


def put(path: str, params: dict = {}):
    headers = sign(path, params)
    headers['Content-type'] = 'application/x-www-form-urlencoded'
    return requests.put(BASE_URL + path, data=params, headers=headers)


def user():
    path = '/api/user'
    response = get(path)
    print(response.text)


def coin_base_info():
    path = '/api/coin/coin_base_info/simple'
    response = get(path)
    print(response.text)


def coin_pairs_info():
    path = '/api/coin/coin_pairs_param/pairses'
    response = get(path)
    print(response.text)


def entrust(entrust_number: str):
    path = '/api/entrust/info'
    params = {"entrust_number": "entrust_number"}
    response = get(path, params)
    print(response.text)


def order(entrust_number: str):
    path = '/api/entrust/order/info'
    params = {'entrust_number': entrust_number}
    response = get(path, params)
    print(response.text)


def top(coin_code: str, price_coin_code: str, top: int = 10):
    path = '/api/entrust/current/top'
    params = {'coin_code': coin_code, 'price_coin_code': price_coin_code}
    response = post(path, params)
    print(response.text)


def fixed(coin_code: str, price_coin_code: str, direction: str, price: str, count: str, password: str):
    path = '/api/engine/entrust/fix'
    params = {'coin_code': coin_code,
              'price_coin_code': price_coin_code,
              'direction': direction,
              'entrust_price': price,
              'entrust_count': count,
              'trade_password': password
              }
    response = post(path, params)
    print(response.text)


def cancel(entrust_number: str):
    path = '/api/engine/entrust/cancel/v1'
    params = {'entrust_number': entrust_number}
    response = put(path, params)
    print(response.text)


if __name__ == '__main__':
    order('0000')
