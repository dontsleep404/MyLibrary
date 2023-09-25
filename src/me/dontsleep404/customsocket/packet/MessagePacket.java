package me.dontsleep404.customsocket.packet;

public class MessagePacket extends Packet{
    
    private String message = null;

    public MessagePacket(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
