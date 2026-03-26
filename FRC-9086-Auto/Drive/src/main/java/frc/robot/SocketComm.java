package frc.robot;

import java.io.*;
import java.net.*;

public class SocketComm {
    private String hostIp;
    private int hostPort;

    private Socket socket;
    private PrintWriter writer;

    public SocketComm(String hostName, int port) {
        hostIp = hostName;
        hostPort = port;

        try {
            socket = new Socket(hostName, port);
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (UnknownHostException ue) {
            System.err.println("Unkown host...");
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("Some io idk");
            System.err.println(e.getMessage());
        }
    }

    
}
