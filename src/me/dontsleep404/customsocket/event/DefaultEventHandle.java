package me.dontsleep404.customsocket.event;

import java.util.HashMap;

import me.dontsleep404.customsocket.DClient;
import me.dontsleep404.customsocket.packet.MessagePacket;
import me.dontsleep404.customsocket.packet.Packet;

public class DefaultEventHandle extends EventHandle{

    public DefaultEventHandle() {
        super(new HashMap<String, Class<? extends Packet>>(){
            {
                put(MessagePacket.class.getSimpleName(), MessagePacket.class);
            }
        });
    }

    @Override
    public void onConnect(EventPacket eventPacket) {
        System.out.println("Connected to server");
    }

    @Override
    public void onDisconnect(EventPacket eventPacket) {
        System.out.println("Disconnected from server");
    }

    @Override
    public void onPacketReceived(DClient client, Packet packet) {
        System.out.println("Received packet: " + client);
    }

    @Override
    public void onSentPacket(DClient client, Packet packet) {
        System.out.println("Sent packet: " + packet);
        client.sendRawPacket(packet.toRawPacket());
    }
    
}
