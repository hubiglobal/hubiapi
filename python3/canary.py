#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# pip3 install requests

import logging
from enum import Enum
from base import BaseClient


class DirectionType(Enum):
    OPENLONG = 1
    OPENSHORT = 2
    CLOSELONG = 3
    CLOSESHORT = 4


class ExchangePlatform(Enum):
    HUBI = 'HUBI'
    OKEX = 'OKEX'
    HUOBI = 'HUOBI'
    BITMEX = 'BITMEX'


class OrderType(Enum):
    LIMIT = 'LIMIT'
    MARKET = 'MARKET'
    ALGO = 'ALGO'


class AlgoType(Enum):
    LIMIT = 'LIMIT'
    MARKET = 'MARKET'


log = logging.getLogger('CanaryClient')


class CanaryClient(BaseClient):
    def __init__(self, base_url, key, secret, token):
        super(CanaryClient, self).__init__(base_url, key, secret, token)

    def asset(self, platform: ExchangePlatform, symbol: str, coinCode: str):
        r"""获取资产

        """
        path = '/api/canary/partner/asset'
        params = {
            'platform': platform,
            'symbol': symbol,
            'coinCode': coinCode
        }
        response = self.get(path, params)
        log.debug(response.text)
        return response

    def order_current_current(self, platform: ExchangePlatform, symbol: str, page=0, size=10):
        r"""当前委托

        """
        path = '/api/canary/order/current/list'
        params = {
            'platform': platform,
            'symbol': symbol,
            'page': page,
            'size': size
        }
        response = self.post(path, json=params)
        log.debug(response.text)
        return response

    def position(self, platform: ExchangePlatform, symbol: str, page=0, size=10):
        r"""当前仓位

        """
        path = '/api/canary/order/position'
        params = {
            'platform': platform,
            'symbol': symbol,
            'page': page,
            'size': size
        }
        response = self.post(path, json=params)
        log.debug(response.text)
        return response

    def order_place(self, platform: ExchangePlatform, coin_code: str, symbol: str, direction: DirectionType, size: str, order_type: OrderType,
                    price: str = None, trigger_price: str = None, algo_price: str = None, algo_type: AlgoType = None,
                    stop_win_price: str = None, stop_loss_price: str = None):
        r"""下单
        :param platform: 平台
        :param coin_code: 结算币种
        :param symbol: 合约， eg: BTCUSD, BCHUSD
        :param open_position: 开平仓， 开仓：true, 平仓：false
        :param quantity: 数量
        :param direction: OPENLONG, OPENSHORT,CLOSELONG,CLOSESHORT
        :param order_type: 限价: LIMIT，市价: MARKET，止盈止损：ALGO
        :param price: 价格
        :param stop_loss_price: 不能和追踪止损（trailingStop）同时设置
        :param trailing_stop: 追踪止损，不能和止损价格（stopLossPrice）同时设置
        :param stop_win_price: 止盈价格
        :param stop_win_type: 限价 LIMIT，市价 MARKET
        :param trigger_price: 条件触发价
        :param algo_price: 止盈止损价格

        :return 返回订单号
        """
        path = '/api/canary/order/place'
        params = {
            'platform': platform,
            'symbol': symbol,
            'coin_code': coin_code,
            'size': size,
            'direction': direction,
            'order_type': order_type
        }

        if price is not None:
            params['price'] = price
        if trigger_price is not None:
            params['triggerPrice'] = trigger_price
        if algo_price is not None:
            params['algoPrice'] = algo_price
        if algo_type is not None:
            params['algoType'] = algo_type
        if stop_win_price is not None:
            params['stopWinPrice'] = stop_win_price
        if stop_loss_price is not None:
            params['stopLossPrice'] = stop_loss_price

        response = self.post(path, json=params)
        log.debug(response.text)
        return response

    def order_cancel(self, platform: ExchangePlatform, order_id: str, type: AlgoType):
        r"""撤销合约订单

        :param platform 平台
        :param order_id 合约ID,eg BTCUSD,ETHUSD
        :param type SWAP：普通合约，ALGO：止盈止损
        """
        path = '/api/canary/order/cancel/order'
        params = {
            'platform': platform,
            'order_id': order_id,
            'type': type,
        }
        response = self.put(path, params)
        log.debug(response.text)
        return response

    def algo_current_list(self, platform: ExchangePlatform, symbol: str, page=0, size=10):
        r"""当前委托 - 条件单

        :param platform 平台
        :param symbol 合约ID,eg BTCUSD,ETHUSD
        :param page 分页起始页，默认值 0
        :param size 每页大小，默认 10
        """
        path = '/api/canary/order/algo/current/list'
        params = {
            'platform': platform,
            'symbol': symbol,
            'page': page,
            'size': size
        }
        response = self.post(path, json=params)
        log.debug(response.text)
        return response

    def order_fills(self, platform: ExchangePlatform, symbol: str, begin: str, end: str, page: int = 0, size: int = 10):
        r"""成交历史

        :param platform 平台
        :param symbol 合约ID,eg BTCUSD,ETHUSD
        :param begin 起始时间，格式：yyyy-MM-dd HH:mm:ss
        :param end 截至时间，格式：yyyy-MM-dd HH:mm:ss
        :param page 分页起始页，默认值 0
        :param size 每页大小，默认 10
        """
        path = '/api/canary/order/fills'
        params = {
            'platform': platform,
            'symbol': symbol,
            'begin': begin,
            'end': end,
            'page': page,
            'size': size
        }
        response = self.post(path, json=params)
        log.debug(response.text)
        return response

    def order_history_list(self, platform: ExchangePlatform, symbol: str, begin: str, end: str, client_oid: str = None, order_id: str = None,
                           page: int = 0, size: int = 10):
        r"""委托历史

        :param platform 平台
        :param symbol 合约ID,eg BTCUSD,ETHUSD
        :param begin 起始时间，格式：yyyy-MM-dd HH:mm:ss
        :param end 截至时间，格式：yyyy-MM-dd HH:mm:ss
        :param client_oid 委托单ID
        :param order_id 第三方委托单ID
        """
        params = {
            'platform': platform,
            'symbol': symbol,
            'begin': begin,
            'end': end,
            'page': page,
            'size': size
        }

        if client_oid is not None:
            params['clientOid'] = client_oid
        if order_id is not None:
            params['orderId'] = order_id

        path = '/api/canary/order/history/list'
        response = self.post(path, json=params)
        log.debug(response.text)
        return response


if __name__ == '__main__':
    # BASE_URL = 'https://api-canary.hubiplus.com'
    # KEY = '***'
    # SECRET = '***'
    # ACCESS_TOKEN = '***'

    # canary = CanaryClient(BASE_URL, KEY, SECRET, ACCESS_TOKEN)
    # canary.asset('HUBI', 'BTCUSDT', 'USDT')
    # canary.order_current_current('HUBI', 'ETTUSDT')
