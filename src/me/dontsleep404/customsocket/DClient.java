package me.dontsleep404.customsocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import com.google.gson.Gson;

import me.dontsleep404.customsocket.event.EnumEvent;
import me.dontsleep404.customsocket.event.EventHandle;
import me.dontsleep404.customsocket.event.EventPacket;
import me.dontsleep404.customsocket.packet.Packet;
import me.dontsleep404.customsocket.packet.RawPacket;

public class DClient {

    private Socket socket = null;
    private String host = null;
    private int port = 0;
    private DataInputStream dataInputStream = null;
    private DataOutputStream dataOutputStream = null;
    private EventHandle eventHandle = null;

    public DClient(Socket socket) {
        this.socket = socket;
    }
    
    public DClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    public void setEventHandle(EventHandle eventHandle) {
        this.eventHandle = eventHandle;
    }
    public boolean connect() {
        try {
            if (socket == null) {
                socket = new Socket(host, port);
            }
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    private void onEvent(EventPacket eventPacket) {
        if (eventHandle != null) {
            eventHandle.handle(eventPacket);
        }
    }
    public void listen(){
        new Thread(() -> {            
            onEvent(new EventPacket(this, EnumEvent.CONNECT, null));
            while (true) {
                try {
                    String message = dataInputStream.readUTF();
                    try{
                        Gson gson = new Gson();
                        RawPacket rawPacket = gson.fromJson(message, RawPacket.class);
                        onEvent(new EventPacket(this, EnumEvent.PACKET_RECEIVED, rawPacket));
                    } catch (Exception e) { // Packet ko hợp lệ                        
                        continue;
                    }
                } catch (Exception e) { // Client bị mất kết nối
                    disconnect();
                    break;
                }
            }
        }).start();
    }
    public void sendPacket(Packet packet){
        if (eventHandle != null) {
            eventHandle.handle(new EventPacket(this, EnumEvent.PACKET_SENT, packet.toRawPacket()));
        }
    }
    public void sendRawPacket(RawPacket rawPacket) {
        try {
            dataOutputStream.writeUTF(rawPacket.toString());
            dataOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void disconnect() {
        try {
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
            onEvent(new EventPacket(this, EnumEvent.DISCONNECT, null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
