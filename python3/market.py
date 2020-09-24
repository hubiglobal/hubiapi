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
        [
          {
            "lotSize": 1.0,
            "rate": null,
            "symbol": "BTCUSD",
            "tick": 1.0,
            "type": "PERP"
          },
          {
            "lotSize": 1.0,
            "rate": null,
            "symbol": "ETHUSD",
            "tick": 0.05,
            "type": "PERP"
          },
          {
            "lotSize": 1.0,
            "rate": null,
            "symbol": "EOSUSD",
            "tick": 0.001,
            "type": "PERP"
          }
        ]

        """
        path = '/api/futures/public/ref_data'
        response = self.get(path)
        print(response.text)
        return response

    def last_price(self):
        r"""获取合约最新成交价

        :return
        {
          "BANDUSD": 5.86,
          "BCHUSD": 209.35,
          "BTCUSD": 10291.0,
          "COMPUSD": 130.5,
          "CRVUSD": 0.905,
          "DOTUSD": 4.085,
          "EOSUSD": 2.455,
          "ETHUSD": 323.5,
          "LINKUSD": 8.04,
          "LTCUSD": 43.53,
          "TRXUSD": 0.02482,
          "UNIUSD": 4.615,
          "XTZUSD": 2.0,
          "YFIUSD": 21900.0
        }
        """
        path = '/api/futures/public/last_price'
        response = self.get(path)
        print(response.text)
        return response

    def price(self, symbols: str):
        r"""获取标记价
        请求频率限定：5次/秒/IP。

        :param symbols 标记名称，多个标记以逗号分隔，eg: BTCUSD,ETHUSD
        :return
        [
          {
            "symbol": "BTCUSD",
            "source": "INDEX",
            "updatedTime": "2020-12-30T12:04:56.859+0000",
            "value": 12063.67525,
            "qty": 0
          }
        ]
        """
        path = '/api/futures/public/index_price'
        params = {'symbols': symbols}
        response = self.get(path, params)
        print(response.text)
        return response

    def depth(self, symbol: str):
        r"""获取市场深度

        :param symbol 合约名称，eg: BTCUSD。
        :return
        
        """
        path = '/api/futures/public/depth/depth'
        params = {'symbol': symbol}
        response = self.get(path, params)
        print(response.text)
        return response

    def trades(self, symbol: str, sequence: str):
        r"""获取最新成交记录; 5次/秒/IP

        :param symbol 合约名称，eg: BTCUSD
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

        path = '/api/futures/public/kline/by_index'
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

        path = '/api/futures/public/kline/by_time'
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

        path = '/api/futures/public/kline/latest'
        params = {'symbol': symbol, 'type': type}
        response = self.get(path, params)
        print(response.text)
        return response

    def funding_rate(self, symbols: str):
        r"""获取资金费率
        请求频次限定：5次/秒/IP。

        :param symbols 合约名称，多个合约以逗号分隔，eg: BTCUSD,ETHUSD
        """

        path = '/api/futures/public/kline/funding_rate'
        params = {'symbols': symbols}
        response = self.get(path, params)
        print(response.text)
        return response

    def trade_stats(self, symbols: str):
        r"""获取最近24小时的成交统计数据。
        请求频次限定：5次/秒/IP

        :param symbols 合约名称，多个合约以逗号分隔，eg: BTCUSD,ETHUSD
        """

        path = '/api/futures/public/kline/trade_statistics'
        params = {'symbols': symbols}
        response = self.get(path, params)
        print(response.text)
        return response

    def open_interest(self, symbol: str):
        r"""获取系统合约持仓量。
        请求频次限定：5次/秒/IP

        :param symbol 合约名称，eg: BTCUSD
        """

        path = '/api/futures/public/kline/open_interest'
        params = {'symbol': symbol}
        response = self.get(path, params)
        print(response.text)
        return response


if __name__ == '__main__':
    BASE_URL = 'https://api.hubi.com'
    KEY = '***'
    SECRET = '***'
    ACCESS_TOKEN = '***'

    market = MarketClient(BASE_URL, KEY, SECRET, ACCESS_TOKEN)
    market.ref_data()
    # market.last_price()
    # market.price('BTCUSD,ETHUSD')
    # market.depth('BTCUSD')
    # market.trades('BTCUSD', '')
    # market.kline_by_index('BTCUSD', '5M', 10, 0)
    # market.kline_by_time('BTCUSD', '5M', 1)
    # market.kline_latest('BTCUSD', '5M')
    # market.fundingRate('BTCUSD,ETHUSD')
    # market.trade_stats('BTCUSD,ETHUSD')
    # market.open_interest('BTCUSD')
