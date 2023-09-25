package me.dontsleep404.customsocket.event;

/**
 * An enumeration of events that can occur in a custom socket connection.
 * 
 * <p>The events are:</p>
 * <ul>
 *   <li>{@link #CONNECT} - A connection has been established.</li>
 *   <li>{@link #DISCONNECT} - A connection has been closed.</li>
 *   <li>{@link #PACKET_RECEIVED} - A packet has been received.</li>
 *   <li>{@link #PACKET_SENT} - A packet has been sent.</li>
 * </ul>
 */
public enum EnumEvent {
    CONNECT,
    DISCONNECT,
    PACKET_RECEIVED,
    PACKET_SENT
}
