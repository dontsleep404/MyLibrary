package test.packet;

import me.dontsleep404.customsocket.packet.Packet;

public class PacketMessage extends Packet{
    enum Type{
        MESSAGE,
        FILE
    }
    String message;
    Type type;
    String fileName;
    byte[] fileData;
}


