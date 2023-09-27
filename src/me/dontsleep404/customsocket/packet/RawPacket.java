package me.dontsleep404.customsocket.packet;

import com.google.gson.Gson;

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
    public Packet toPacket(Class<? extends Packet> packetClass) {
        if (!packetName.equals(packetClass.getSimpleName())) return null;
        try {
            Gson gson = new Gson();
            return gson.fromJson(packetData, packetClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
