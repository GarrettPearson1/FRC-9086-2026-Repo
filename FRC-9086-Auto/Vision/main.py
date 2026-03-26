import os
import time

import cv2
from dotenv import load_dotenv

from Aprl import Aprl
from CameraCalibrationData import load_calibration
from Camera import Camera, Frame
from Metavison import Metavision

import rerun as rr
import numpy as np

if __name__ == "__main__":
    load_dotenv()

    camera = Camera(0, load_calibration("data/" + os.getenv("CALIBRATION_FILE")), int(os.getenv("RESOLUTION_WIDTH")), int(os.getenv("RESOLUTION_HEIGHT")))

    aprl = Aprl()
    metavision = Metavision()

    while True:
        time.sleep(.01666)
        results = aprl.detect(camera)

        metavision.update_aprltags(results)
        print(metavision.current_translation)
        #frame = metavision.visualize(camera.frame, results)

        #cv2.imshow("Aprltag", frame)
        #cv2.waitKey(1)