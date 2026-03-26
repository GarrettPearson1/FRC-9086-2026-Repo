import io
import struct

import numpy as np
from dataclasses import dataclass

from LocalDataHandler import LocalDataHandler


@dataclass
class CameraCalibrationData:
    matrix: np.ndarray
    distCoeffs: np.ndarray
    rvecs: np.ndarray
    tvecs: np.ndarray

def save_calibration(filePath: str, data: CameraCalibrationData):
    matrix_bytes = io.BytesIO()
    dist_bytes = io.BytesIO()
    rvecs_bytes = io.BytesIO()
    tvecs_bytes = io.BytesIO()

    np.save(matrix_bytes, data.matrix)
    np.save(dist_bytes, data.distCoeffs)
    np.save(rvecs_bytes, data.rvecs)
    np.save(tvecs_bytes, data.tvecs)

    wb_data = [
        bytes("9086-CCD", "utf-8"),
        matrix_bytes.getbuffer().nbytes.to_bytes(8, "little"),
        dist_bytes.getbuffer().nbytes.to_bytes(8, "little"),
        rvecs_bytes.getbuffer().nbytes.to_bytes(8, "little"),
        tvecs_bytes.getbuffer().nbytes.to_bytes(8, "little"),

        matrix_bytes.getvalue(),
        dist_bytes.getvalue(),
        rvecs_bytes.getvalue(),
        tvecs_bytes.getvalue()
    ]

    with open(filePath, "wb") as file:
        for d in wb_data:
            file.write(d)

def load_calibration(filePath: str) -> CameraCalibrationData:
    matrix = None
    dist = None
    rvecs = None
    tvecs = None

    with open(filePath, "rb") as file:
        if file.read(8) != b"9086-CCD":
            raise Exception("Invalid file magic")

        section_sizes = [
            int.from_bytes(file.read(8), "little"),
            int.from_bytes(file.read(8), "little"),
            int.from_bytes(file.read(8), "little"),
            int.from_bytes(file.read(8), "little"),
        ]

        matrix = np.load(io.BytesIO(file.read(section_sizes[0])))
        dist = np.load(io.BytesIO(file.read(section_sizes[1])))
        rvecs = np.load(io.BytesIO(file.read(section_sizes[2])))
        tvecs = np.load(io.BytesIO(file.read(section_sizes[3])))

    return CameraCalibrationData(matrix, dist, rvecs, tvecs)