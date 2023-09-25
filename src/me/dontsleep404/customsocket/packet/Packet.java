package me.dontsleep404.customsocket.packet;

import com.google.gson.Gson;

public class Packet {
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public RawPacket toRawPacket() {
        return new RawPacket(this.getClass().getSimpleName(), this.toString());
    }
}
