import json

import numpy


class LocalDataHandler:
    @staticmethod
    def get_json_data(file_name: str) -> any:
        with open("data/" + file_name, 'r') as handle:
            return json.loads(handle.read())

    @staticmethod
    def get_numpy_data(file_name: str):
        return numpy.load("data/" + file_name)