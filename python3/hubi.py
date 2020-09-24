#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# pip3 install requests

import logging
from base import BaseClient

log = logging.getLogger('HubiClient')


class HubiClient(BaseClient):
    def __init__(self, base_url, key, secret, token):
        super(HubiClient, self).__init__(base_url, key, secret, token)

    def user(self):
        path = '/api/user'
        response = self.get(path)
        log.debug(response.text)
        return response

    def coin_base_info(self):
        path = '/api/coin/coin_base_info/simple'
        response = self.get(path)
        log.debug(response.text)
        return response

    def coin_pairs_info(self):
        path = '/api/coin/coin_pairs_param/pairses'
        response = self.get(path)
        log.debug(response.text)
        return response

    def entrust(self, entrust_number: str):
        path = '/api/entrust/info'
        params = {"entrust_number": "entrust_number"}
        response = self.get(path, params)
        log.debug(response.text)
        return response

    def order(self, entrust_number: str):
        path = '/api/entrust/order/info'
        params = {'entrust_number': entrust_number}
        response = self.get(path, params)
        log.debug(response.text)
        return response

    def top(self, coin_code: str, price_coin_code: str, top: int = 10):
        path = '/api/entrust/current/top'
        params = {'coin_code': coin_code, 'price_coin_code': price_coin_code}
        response = self.post(path, params)
        log.debug(response.text)
        return response

    def fixed(self, coin_code: str, price_coin_code: str, direction: str, price: str, count: str, password: str):
        path = '/api/engine/entrust/fix'
        params = {'coin_code': coin_code,
                  'price_coin_code': price_coin_code,
                  'direction': direction,
                  'entrust_price': price,
                  'entrust_count': count,
                  'trade_password': password
                  }
        response = self.post(path, params)
        log.debug(response.text)
        return response

    def cancel(self, entrust_number: str):
        path = '/api/engine/entrust/cancel/v1'
        params = {'entrust_number': entrust_number}
        response = self.put(path, params)
        log.debug(response.text)
        return response


if __name__ == '__main__':
    BASE_URL = 'https://api.hubi.com'
    KEY = '***'
    SECRET = '***'
    ACCESS_TOKEN = '***'

    client = HubiClient(BASE_URL, KEY, SECRET, ACCESS_TOKEN)
    # client.user()
