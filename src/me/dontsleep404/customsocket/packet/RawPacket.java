package me.dontsleep404.customsocket.packet;

public class RawPacket extends Packet{
    private String packetName;
    private String packetData;
    public RawPacket(String packetName, String packetData) {
        this.packetName = packetName;
        this.packetData = packetData;
    }
    public String getPacketName() {
        return packetName;
    }
    public String getPacketData() {
        return packetData;
    }
}
