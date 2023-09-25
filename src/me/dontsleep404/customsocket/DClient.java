package me.dontsleep404.customsocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

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
                    System.out.println(message);
                } catch (Exception e) {
                    break;
                }
            }
        }).start();
    }
    public void sendPacket(Packet packet){
        if (eventHandle != null) {
            eventHandle.handle(new EventPacket(this, EnumEvent.PACKET_SENT, packet));
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception{
        DClient client = new DClient("localhost", 8080);
        client.connect();
        client.listen();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String message = scanner.nextLine();
            if (message.equalsIgnoreCase("exit")) {
                client.disconnect();
                break;
            }
            // client.sendMessage(message);
        }
        scanner.close();
    }

}
