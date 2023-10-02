package me.dontsleep404.customsocket.event;

import java.util.ArrayList;
import java.util.HashMap;

import me.dontsleep404.customsocket.DClient;
import me.dontsleep404.customsocket.packet.Packet;
import me.dontsleep404.customsocket.packet.RawPacket;

public abstract class EventHandle {

    private HashMap<String, Class<? extends Packet>> acceptedPackets;

    public EventHandle(HashMap<String, Class<? extends Packet>> acceptedPackets) {
        this.acceptedPackets = acceptedPackets;
    }
    public EventHandle(ArrayList<Class<? extends Packet>> acceptedPackets) {
        this.acceptedPackets = new HashMap<String, Class<? extends Packet>>();
        for(Class<? extends Packet> packet : acceptedPackets){
            this.acceptedPackets.put(packet.getSimpleName(), packet);
        }
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
                            .toPacket(getAcceptedPackets().get(eventPacket.getPacket().getPacketName())) != null){
                                try{
                                    Packet packet = eventPacket.getPacket().toPacket(getAcceptedPackets().get(eventPacket.getPacket().getPacketName()));
                                    onPacketReceived(eventPacket.getClient(), packet);
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                        }
                break;
            case PACKET_SENT:
                if (eventPacket.getPacket() != null
                        && getAcceptedPackets().containsKey(eventPacket.getPacket().getPacketName()))
                    if (eventPacket.getPacket()
                            .toPacket(getAcceptedPackets().get(eventPacket.getPacket().getPacketName())) != null){
                                try{
                                    Packet packet = eventPacket.getPacket().toPacket(getAcceptedPackets().get(eventPacket.getPacket().getPacketName()));
                                    onSentPacket(eventPacket.getClient(), packet);
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                            }
                break;
        }
    }

    public abstract void onConnect(EventPacket eventPacket);

    public abstract void onDisconnect(EventPacket eventPacket);

    public abstract void onPacketReceived(DClient client, Packet packet);

    public void onSentPacket(DClient client, Packet packet){
        if(packet instanceof RawPacket) client.sendRawPacket((RawPacket) packet);
        else client.sendRawPacket(packet.toRawPacket());
    }

}
