import socket

def client_program():
    host = socket.gethostname()
    port = 9086

    client_socket = socket.socket()
    try:
        client_socket.connect((host, port))
    except ConnectionRefusedError:
        print("The connection to the server was refused")
        return

    while True:
        client_socket.send("POS".encode())
        data = client_socket.recv(1024).decode()
        print(data)

    #message = input(" -> ")
    #while message.lower().strip() != 'bye':
    #    client_socket.send(message.encode())
    #    data = client_socket.recv(1024).decode()
    #    print('Response from server: ' + data)
    #    message = input(" -> ")

    client_socket.close()

if __name__ == '__main__':
    client_program()