package me.dontsleep404.customsocket;

import java.net.ServerSocket;

import me.dontsleep404.customsocket.event.DefaultEventHandle;
import me.dontsleep404.customsocket.event.EventHandle;

public class DServer {
    
    private int port = 0;
    private ServerSocket serverSocket = null;
    private EventHandle eventHandle = null;
    private boolean listening = false;

    public DServer(int port) {
        this.port = port;
    }
    public boolean listen() {
        try {
            serverSocket = new ServerSocket(port);
            listening = true;
            new Thread(() -> {
                while (listening) {
                    try {
                        DClient client = new DClient(serverSocket.accept());
                        client.setEventHandle(eventHandle);
                        if (client.connect())
                            client.listen();
                    } catch (Exception e) {
                        continue;
                    }
                }
            }).start();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void stop() {
        listening = false;
        try {
            if (serverSocket != null) serverSocket.close();
        } catch (Exception e) {
            return;
        }
    }

    public void setEventHandle(EventHandle eventHandle) {
        this.eventHandle = eventHandle;
    }
    public static void main(String[] args) {
        DServer server = new DServer(8080);
        server.setEventHandle(new DefaultEventHandle());
        server.listen();
    }
}
