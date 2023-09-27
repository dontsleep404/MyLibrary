package me.dontsleep404.customsocket.event;

import java.util.HashMap;

import me.dontsleep404.customsocket.packet.Packet;

public abstract class EventHandle {

    private HashMap<String, Class<? extends Packet>> acceptedPackets;

    public EventHandle(HashMap<String, Class<? extends Packet>> acceptedPackets) {
        this.acceptedPackets = acceptedPackets;
    }

    public HashMap<String, Class<? extends Packet>> getAcceptedPackets() {
        return acceptedPackets;
    }

    public void handle(EventPacket eventPacket) {
        switch (eventPacket.getEvent()) {
            case CONNECT:
                onConnect(eventPacket);
                break;
            case DISCONNECT:
                onDisconnect(eventPacket);
                break;
            case PACKET_RECEIVED:
                if (eventPacket.getPacket() != null
                        && getAcceptedPackets().containsKey(eventPacket.getPacket().getPacketName()))
                    if (eventPacket.getPacket()
                            .toPacket(getAcceptedPackets().get(eventPacket.getPacket().getPacketName())) != null)
                        onPacketReceived(eventPacket);
                break;
            case PACKET_SENT:
                if (eventPacket.getPacket() != null
                        && getAcceptedPackets().containsKey(eventPacket.getPacket().getPacketName()))
                    if (eventPacket.getPacket()
                            .toPacket(getAcceptedPackets().get(eventPacket.getPacket().getPacketName())) != null)
                        onSentPacket(eventPacket);
                break;
        }
    }

    public abstract void onConnect(EventPacket eventPacket);

    public abstract void onDisconnect(EventPacket eventPacket);

    public abstract void onPacketReceived(EventPacket eventPacket);

    public abstract void onSentPacket(EventPacket eventPacket);

}
