import cv2
import threading
import numpy as np

from CameraCalibrationData import CameraCalibrationData

Frame = np.ndarray[any, np.dtype]

class Camera:
    capture: cv2.VideoCapture
    calibration: CameraCalibrationData
    watcher_threader: threading.Thread

    frame: Frame

    def __init__(self, camera_num: int, calibration: CameraCalibrationData, width: int = 640, height: int = 480):
        self.calibration = calibration

        self.capture = cv2.VideoCapture(camera_num, cv2.CAP_DSHOW)
        self.capture.set(cv2.CAP_PROP_FRAME_WIDTH, width)
        self.capture.set(cv2.CAP_PROP_FRAME_HEIGHT, height)
        self.capture.set(cv2.CAP_PROP_FOURCC, cv2.VideoWriter_fourcc(*"MJPG"))

        r, f = self.capture.read()
        self.frame = f

        def watcher():
            while True:
                ret, frame = self.capture.read()
                if ret:
                    self.frame = frame

        self.watcher_threader = threading.Thread(target=watcher)
        self.watcher_threader.start()