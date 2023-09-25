package me.dontsleep404.customsocket.event;

import me.dontsleep404.customsocket.DClient;
import me.dontsleep404.customsocket.packet.Packet;

public class EventPacket {

    private DClient client;
    private EnumEvent event;
    private Packet packet;
    private boolean isCancelled = false;
    
    /**
     * Constructs a new EventPacket object with the specified client, event and packet.
     *
     * @param client the DClient object associated with this event packet
     * @param event the EnumEvent object representing the type of event
     * @param packet the Packet object containing the data for this event
     */
    public EventPacket(DClient client, EnumEvent event, Packet packet) {
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
    public Packet getPacket() {
        return packet;
    }
    
    /**
     * Sets the Packet object containing the data for this event.
     *
     * @param packet the Packet object containing the data for this event
     */
    public void setPacket(Packet packet) {
        this.packet = packet;
    }
    
    /**
     * Sets whether this event is cancelled.
     *
     * @param cancelled true if this event is cancelled, false otherwise
     */
    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
    
    /**
     * Returns whether this event is cancelled.
     *
     * @return true if this event is cancelled, false otherwise
     */
    public boolean isCancelled() {
        return isCancelled;
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
