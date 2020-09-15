#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# pip3 install requests


from base import BaseClient


class MarketClient(BaseClient):
    def __init__(self, base_url, key, secret, token):
        super(MarketClient, self).__init__(base_url, key, secret, token)

    def ref_data(self):
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
            }
        ]
        }

        """
        path = '/api/futures/public/basic/refData'
        response = self.get(path)
        print(response.text)
        return response

    def last_price(self):
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
        response = self.get(path)
        print(response.text)
        return response

    def price(self, symbols: str):
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
        response = self.get(path, params)
        print(response.text)
        return response

    def depth(self, symbol: str):
        r"""获取市场深度

        :param symbol 合约名称，eg: XBTCUSD。
        :return

        """
        path = '/api/futures/public/depth/depth'
        params = {'symbol': symbol}
        response = self.get(path, params)
        print(response.text)
        return response

    def trades(self, symbol: str, sequence: str):
        r"""获取最新成交记录; 5次/秒/IP

        :param symbol 合约名称，eg: XBTCUSD
        :param sequence 上一次获取成交记录的最新一笔记录的 id；第一次请求传入空值，
        :result 返回最近50笔成交。
        """
        path = '/api/futures/public/depth/trades'
        params = {'symbol': symbol, 'sequence': sequence}
        response = self.get(path, params)
        print(response.text)
        return response

    def kline_by_index(self, symbol: str, type: str, step: int, time):
        r"""通过下标索引获取历史 K 线
        请求频次限定：15次/分/IP
        """

        path = '/api/futures/public/kLine/byIndex'
        params = {
            'symbol': symbol,
            'type': type,
            'step': step,
            'from': time
        }
        response = self.get(path, params)
        print(response.text)
        return response

    def kline_by_time(self, symbol: str, type: str, step: int, time=None):
        r"""通过时间索引获取历史 K 线
        请求频次限定：15次/分/IP
        """

        path = '/api/futures/public/kLine/byTime'
        params = {
            'symbol': symbol,
            'type': type,
            'step': step
        }

        if time is not None:
            params['from'] = time,

        response = self.get(path, params)
        print(response.text)
        return response

    def kline_latest(self, symbol: str, type: str):
        r"""获取最新一根 K 线数据。
        请求频次限定：75次/分/IP
        """

        path = '/api/futures/public/kLine/latest'
        params = {'symbol': symbol, 'type': type}
        response = self.get(path, params)
        print(response.text)
        return response

    def fundingRate(self, symbols: str):
        r"""获取资金费率
        请求频次限定：5次/秒/IP。

        :param symbols 合约名称，多个合约以逗号分隔，eg: XBTCUSD,XETHUSD
        """

        path = '/api/futures/public/kLine/fundingRate'
        params = {'symbols': symbols}
        response = self.get(path, params)
        print(response.text)
        return response

    def trade_stats(self, symbols: str):
        r"""获取最近24小时的成交统计数据。
        请求频次限定：5次/秒/IP

        :param symbols 合约名称，多个合约以逗号分隔，eg: XBTCUSD,XETHUSD
        """

        path = '/api/futures/public/kLine/tradeStatistics'
        params = {'symbols': symbols}
        response = self.get(path, params)
        print(response.text)
        return response

    def open_interest(self, symbol: str):
        r"""获取系统合约持仓量。
        请求频次限定：5次/秒/IP

        :param symbol 合约名称，eg: BTCUSD （注意：和合约名称进行区分，标记名前去掉X）
        """

        path = '/api/futures/public/kLine/openInterest'
        params = {'symbol': symbol}
        response = self.get(path, params)
        print(response.text)
        return response
