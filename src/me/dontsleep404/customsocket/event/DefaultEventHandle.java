package me.dontsleep404.customsocket.event;

import java.util.HashMap;

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
    public void onPacketReceived(EventPacket eventPacket, Class<? extends Packet> packetClass) {
        System.out.println("Received packet: " + eventPacket.getPacket());
    }

    @Override
    public void onSentPacket(EventPacket eventPacket, Class<? extends Packet> packetClass) {
        System.out.println("Sent packet: " + eventPacket.getPacket());
        eventPacket.getClient().sendRawPacket(eventPacket.getPacket());
    }
    
}
