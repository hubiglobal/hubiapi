#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# pip3 install requests

import logging
from base import BaseClient

log = logging.getLogger('TraceClient')


class TraceClient(BaseClient):
    def __init__(self, base_url, key, secret, token):
        super(TraceClient, self).__init__(base_url, key, secret, token)

    def list_symbol(self):
        r"""合约列表

        """
        path = '/api/futures/public/list_symbol'
        response = self.get(path)
        log.debug(response.text)
        return response

    def query_active_orders(self, symbol: str):
        r"""查询用户未完成的订单

        :param symbol 合约， eg: BTCUSD, BCHUSD...
        """
        path = '/api/futures/query_active_orders'
        params = {'symbol': symbol}
        response = self.get(path, params)
        log.debug(response.text)
        return response

    def query_orders_pro(self, timestamp):
        r"""查询用户的某个订单

        :param timestamp 上次请求的时间毫秒数
        """
        path = '/api/futures/query_orders_pro'
        params = {"timestamp": timestamp}
        response = self.get(path, params)
        log.debug(response.text)
        return response

    def query_order(self, order_no: str):
        r"""查询用户的某个订单

        :param order_no 订单号
        """
        path = '/api/futures/query_order_by_id'
        params = {"order_no": order_no}
        response = self.get(path, params)
        log.debug(response.text)
        return response

    def query_accounts(self):
        r"""查询合约账户
        """
        path = '/api/futures/query_accounts'
        response = self.get(path)
        log.debug(response.text)
        return response

    def query_position(self):
        r"""查询仓位
        """
        path = '/api/futures/query_position'
        response = self.get(path)
        log.debug(response.text)
        return response

    def enter_order(self, coin_code: str, symbol: str, open_position: bool, quantity: str, trade_direction: str, order_type: str,
                    price: str = None, stop_loss_price: str = None, trailing_stop: str = None, stop_win_price: str = None, stop_win_type: str = None,
                    trigger_price: str = None, trigger_type: str = 'LAST', tif_type: str = 'GOOD_TILL_CANCEL'):
        r"""下单
        :param coin_code: 结算币种
        :param symbol: 合约， eg: BTCUSD, BCHUSD
        :param open_position: 开平仓， 开仓：true, 平仓：false
        :param quantity: 数量
        :param trade_direction: BUY, SELL
        :param order_type: 限价: LIMIT，市价: MARKET
        :param price: 价格
        :param stop_loss_price: 不能和追踪止损（trailingStop）同时设置
        :param trailing_stop: 追踪止损，不能和止损价格（stopLossPrice）同时设置
        :param stop_win_price: 止盈价格
        :param stop_win_type: 限价 LIMIT，市价 MARKET
        :param trigger_price: 条件触发价
        :param trigger_type: LAST 为最后成交价触发，INDEX 为标记价触发
        :param tif_type: GOOD_TILL_CANCEL: 一直有效至消失；IMMEDIATE_OR_CANCEL: 立即成交或取消；FILL_OR_KILL:完全成交或取消；QUEUE_OR_CANCEL: 被动委托

        :return 返回订单号
        """
        path = '/api/futures/enter_order'
        params = {'coin_code': coin_code,
                  'symbol': symbol,
                  'open_position': open_position,
                  'quantity': quantity,
                  'trade_direction': trade_direction,
                  'order_type': order_type}

        if price is not None:
            params['price'] = price
        if stop_loss_price is not None:
            params['stop_loss_price'] = stop_loss_price
        if trailing_stop is not None:
            params['trailing_stop'] = trailing_stop
        if stop_win_price is not None:
            params['stop_win_price'] = stop_win_price
        if stop_win_type is not None:
            params['stop_win_type'] = stop_win_type
        if trigger_price is not None:
            params['trigger_price'] = trigger_price
        if trigger_type is not None:
            params['trigger_type'] = trigger_type
        if tif_type is not None:
            params['tif_type'] = tif_type
        response = self.post(path, params)
        log.debug(response.text)
        return response

    def cancel_order(self, order_no: str):
        r"""取消订单

        :param order_no: 订单号
        """
        path = '/api/futures/cancel_order'
        params = {'order_no': order_no}
        response = self.post(path, params)
        log.debug(response.text)
        return response

    def cancel_order_batch(self, order_nos: str):
        r"""批量取消订单

        :param order_nos: 订单号，多个用逗号分隔
        """
        path = '/api/futures/cancel_order_batch'
        params = {'order_nos': order_nos}
        response = self.post(path, params)
        log.debug(response.text)
        return response

    def close_position(self, coin_code: str, symbol: str, position_type: str):
        r"""当前用户合约仓位全平

        :param coin_code: 结算币种
        :param symbol: 合约名字，eg: BTCUSD, BCHUSD...
        :param position_type:多仓 Long，空仓 Short
        """
        path = '/api/futures/close_position'
        params = {'coin_code': coin_code,
                  'symbol': symbol,
                  'position_type': position_type}
        response = self.post(path, params)
        log.debug(response.text)
        return response

    def switch_to_cross(self, coin_code: str):
        r"""当前用户切换为全仓模式

        :param coin_code: 结算币种
        """
        path = '/api/futures/switch_to_cross'
        params = {'coin_code': coin_code}
        response = self.post(path, params)
        log.debug(response.text)
        return response

    def switch_position_side(self, coin_code: str, two_side_position: str):
        r"""当前用户切换单双向持仓模式

        :param coin_code: 结算币种
        :param two_side_position: 双向持仓true, 单向持仓false
        """
        path = '/api/futures/switch_position_side'
        params = {'coin_code': coin_code,
                  'two_side_position': two_side_position}
        response = self.post(path, params)
        log.debug(response.text)
        return response

    def change_position_leverage(self, coin_code: str, symbol: str, leverage: int):
        r"""切换当前用户合约的持仓杠杆

        :param coin_code: 结算币种
        :param symbol: 合约名字，eg:BTCUSD, BCHUSD..
        :param leverage: 杠杆倍数
        """
        path = '/api/futures/change_position_leverage'
        params = {
            'coin_code': coin_code,
            'symbol': symbol,
            'leverage': leverage
        }
        response = self.post(path, params)
        log.debug(response.text)
        return response

    def amend_order(self, order_no: str,
                    quantity: str = None, price: str = None,
                    stop_loss_price: str = None, trailing_stop: str = None,
                    stop_win_price: str = None, stop_win_type: str = None,
                    trigger_price: str = None, trigger_type: str = 'LAST'):
        r"""修改当前用户的合约订单

        :param order_no: 订单编号
        :param quantity: 数量
        :param price: 价格
        :param trigger_price: 条件单触发价
        :param stop_loss_price: 止损价格
        :param trailing_stop: 追踪止损，不能和止损价格（stopLossPrice）同时设置
        :param stop_win_price: 止盈价格
        :param stop_win_type: LIMIT为限价止盈，MARKET为市价止盈
        """
        path = '/api/futures/amend_order'
        params = {'order_no': order_no, }
        if quantity is not None:
            params['quantity'] = quantity
        if price is not None:
            params['price'] = price
        if stop_loss_price is not None:
            params['stop_loss_price'] = stop_loss_price
        if trailing_stop is not None:
            params['trailing_stop'] = trailing_stop
        if stop_win_price is not None:
            params['stop_win_price'] = stop_win_price
        if stop_win_type is not None:
            params['stop_win_type'] = stop_win_type
        if trigger_price is not None:
            params['trigger_price'] = trigger_price
        if trigger_type is not None:
            params['trigger_type'] = trigger_type

        response = self.post(path, params)
        log.debug(response.text)
        return response

    def risk_setting(self, coin_code: str, symbol: str, position_type: str,
                     add_deposit: str = None,
                     stop_loss_price: str = None, trailing_stop: str = None,
                     stop_win_price: str = None, stop_win_type: str = None):
        r"""修改当前用户的合约订单

        :param coin_code: 结算币种
        :param symbol: 合约名字，eg:BTCUSD, BCHUSD..
        :param position_type: LONG为多仓，SHORT为空仓
        :param add_deposit: 追加/减少保证金，只对逐仓模式
        :param stop_loss_price: 止损价格
        :param trailing_stop: 追踪止损，不能和止损价格（stopLossPrice）同时设置
        :param stop_win_price: 止盈价格
        :param stop_win_type: LIMIT为限价止盈，MARKET为市价止盈
        """
        path = '/api/futures/risk_setting'
        params = {'coin_code': coin_code,
                  'symbol': symbol,
                  'position_type': position_type
                  }
        if add_deposit is not None:
            params['add_deposit'] = add_deposit
        if stop_loss_price is not None:
            params['stop_loss_price'] = stop_loss_price
        if trailing_stop is not None:
            params['trailing_stop'] = trailing_stop
        if stop_win_price is not None:
            params['stop_win_price'] = stop_win_price
        if stop_win_type is not None:
            params['stop_win_type'] = stop_win_type

        response = self.post(path, params)
        log.debug(response.text)
        return response


if __name__ == '__main__':
    BASE_URL = 'https://api.hubi.com'
    KEY = '***'
    SECRET = '***'
    ACCESS_TOKEN = '***'

    trade = TraceClient(BASE_URL, KEY, SECRET, ACCESS_TOKEN)
    # trade.cancel_order_batch(
    #     'O150-20200915-092107-767-2460,O150-20200915-113808-323-2224')
    # trade.list_symbol()
    # trade.query_active_orders('ETHUSD')
    # trade.query_accounts()
    # trade.query_position()
    # trade.switch_to_cross('USDT')
    # trade.change_position_leverage('USDT', 'ETHUSD', 20)
    # trade.close_position('USDT', 'ETHUSD', 'LONG')
    # trade.enter_order('USDT', 'ETHUSD', 'true', '100', '372',
    #             'BUY', 'LIMIT', stop_loss_price='200')
    # trade.enter_order('USDT', 'ETHUSD', 'false', '100',  'SELL', 'MARKET')
    # trade.query_order('O150-20200910-061812-837-2192')
    # trade.amend_order('O150-20200910-061812-837-2192', price='373')
    # trade.risk_setting('USDT', 'ETHUSD', 'LONG',
    #              stop_loss_price='300', stop_win_price='400', stop_win_type='MARKET')
