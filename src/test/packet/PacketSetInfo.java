package test.packet;

import me.dontsleep404.customsocket.packet.Packet;

public class PacketSetInfo extends Packet{
    private String name;
    public PacketSetInfo(String name) {
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
