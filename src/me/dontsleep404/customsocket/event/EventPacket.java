package me.dontsleep404.customsocket.event;

import me.dontsleep404.customsocket.DClient;
import me.dontsleep404.customsocket.packet.RawPacket;

public class EventPacket {

    private DClient client;
    private EnumEvent event;
    private RawPacket packet;
    
    /**
     * Constructs a new EventPacket object with the specified client, event and packet.
     *
     * @param client the DClient object associated with this event packet
     * @param event the EnumEvent object representing the type of event
     * @param packet the Packet object containing the data for this event
     */
    public EventPacket(DClient client, EnumEvent event, RawPacket packet) {
        this.event = event;
        this.packet = packet;
        this.client = client;
    }
    
    /**
     * Returns the EnumEvent object representing the type of event.
     *
     * @return the EnumEvent object representing the type of event
     */
    public EnumEvent getEvent() {
        return event;
    }
    
    /**
     * Returns the Packet object containing the data for this event.
     *
     * @return the Packet object containing the data for this event
     */
    public RawPacket getPacket() {
        return packet;
    }   

    /**
     * Returns the DClient object associated with this event packet.
     *
     * @return the DClient object associated with this event packet
     */
    public DClient getClient() {
        return client;
    }
}
