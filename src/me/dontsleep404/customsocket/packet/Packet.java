package me.dontsleep404.customsocket.packet;

import com.google.gson.Gson;

public class Packet {
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public RawPacket toRawPacket() {
        if (this.getClass().getSimpleName().equals("RawPacket")) return (RawPacket) this;
        return new RawPacket(this.getClass().getSimpleName(), this.toString());
    }
}
