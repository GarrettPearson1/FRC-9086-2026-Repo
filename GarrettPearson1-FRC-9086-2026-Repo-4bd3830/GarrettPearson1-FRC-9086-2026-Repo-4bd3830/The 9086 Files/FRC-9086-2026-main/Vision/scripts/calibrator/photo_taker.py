import cv2

resolution_string = input("Resolution: ")
s = resolution_string.split("x")
width = int(s[0].strip())
height = int(s[1].strip())

capture = cv2.VideoCapture(0)
capture.set(cv2.CAP_PROP_FRAME_WIDTH, width)
capture.set(cv2.CAP_PROP_FRAME_HEIGHT, height)

num = 1

while capture.isOpened():
    success, frame = capture.read()

    k = cv2.waitKey(5)

    # Escape key
    if k == 27:
        break
    elif k == ord('s'):
        cv2.imwrite(f"camera2/image{num}.jpg", frame)
        print("Saved: " + str(num))
        num += 1

    cv2.imshow("Picture", frame)

capture.release()