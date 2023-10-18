package me.dontsleep404.customsocket.event;

import me.dontsleep404.customsocket.DClient;
import me.dontsleep404.customsocket.packet.RawPacket;

public class EventPacket {

    private DClient client;
    private EnumEvent event;
    private RawPacket packet;
    
    public EventPacket(DClient client, EnumEvent event, RawPacket packet) {
        this.event = event;
        this.packet = packet;
        this.client = client;
    }
    
    public EnumEvent getEvent() {
        return event;
    }
    
    public RawPacket getPacket() {
        return packet;
    }   

    public DClient getClient() {
        return client;
    }
}
