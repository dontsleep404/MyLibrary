package me.dontsleep404.customsocket;

import java.net.ServerSocket;

public class DServer {
    private int port = 0;
    private ServerSocket serverSocket = null;
    public DServer(int port) {
        this.port = port;
    }
    public boolean start() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public void listen() {
        new Thread(() -> {
            while (true) {
                try {
                    DClient client = new DClient(serverSocket.accept());
                    if (client.connect())
                        client.listen();
                } catch (Exception e) {
                    break;
                }
            }
        }).start();
    }
    public static void main(String[] args) {
        DServer server = new DServer(8080);
        server.start();
        server.listen();
    }
}
