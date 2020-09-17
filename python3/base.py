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


class BaseClient(object):
    __version = '1.0.0'

    def __init__(self, base_url, key, secret, token):
        self.__base_url = base_url
        self.__key = key
        self.__secret = secret.encode(DEFAULT_CHARSET)
        self.__token = token

    def __sign(self, path: str, params: dict = None):
        if params is None:
            params = {}
        timestamp = datetime.datetime.utcnow().isoformat(timespec='milliseconds') + 'Z'
        nonce = f'{self.__key}{timestamp }{random.random()}'
        nonceHash = hashlib.md5(nonce.encode(
            DEFAULT_CHARSET)).hexdigest().lower()

        paramsMapString = urllib.parse.urlencode(params, safe=',')
        content = f'{paramsMapString}{self.__version}{nonceHash}{path}'
        sign = hmac.new(key=self.__secret, msg=content.encode(
            DEFAULT_CHARSET), digestmod=hashlib.sha256).hexdigest().lower()

        print("content: ", content)
        print("sign: ", sign)

        headers = {}
        headers['Authorization'] = 'Bearer ' + self.__token
        headers['X-API-Key'] = self.__key
        headers['X-API-Version'] = self.__version
        headers['X-API-Timestamp'] = timestamp
        headers['X-API-Nonce'] = nonceHash
        headers['X-API-Signature-Params'] = ','.join(params.keys())
        headers['X-API-Signature'] = sign

        return headers

    def get(self, path: str, params: dict = None):
        headers = self.__sign(path, params)
        return requests.get(self.__base_url+path, params=params, headers=headers)

    def delete(self, path: str, params: dict = None):
        headers = self.__sign(path, params)
        return requests.delete(self.__base_url + path, params=params, headers=headers)

    def put(self, path: str, data: dict = None, json: dict = None):
        if json is not None:
            headers = self.__sign(path, json)
            return requests.put(self.__base_url + path, json=json, headers=headers)

        headers = self.__sign(path, data)
        return requests.put(self.__base_url + path, data=data, headers=headers)

    def post(self, path: str, data: dict = None, json: dict = None):
        if json is not None:
            headers = self.__sign(path, json)
            return requests.post(self.__base_url + path, json=json, headers=headers)

        headers = self.__sign(path, data)
        return requests.post(self.__base_url + path, data=data, headers=headers)
