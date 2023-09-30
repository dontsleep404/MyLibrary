package test.server;
import java.util.ArrayList;
import java.util.HashMap;

import me.dontsleep404.customsocket.DClient;
import me.dontsleep404.customsocket.event.EventHandle;
import me.dontsleep404.customsocket.event.EventPacket;
import me.dontsleep404.customsocket.packet.Packet;
import test.packet.PacketConnect;
import test.packet.PacketDisconnect;
import test.packet.PacketMessage;
import test.packet.PacketSetInfo;
public class ServerHandle extends EventHandle{

    public ArrayList<DClient> clients = new ArrayList<DClient>();

    public ServerHandle() {
        super(new HashMap<String, Class<? extends Packet>>(){
            {
                put("PacketMessage", PacketMessage.class);
                put("PacketSetInfo", PacketSetInfo.class);
                put("PacketConnect", PacketConnect.class);
                put("PacketDisconnect", PacketDisconnect.class);
            }
        });
    }
    public void broadcast(Packet packet){
        for(DClient client : clients){
            client.sendPacket(packet);
        }
    }
    @Override
    public void onConnect(EventPacket eventPacket) {
        if (clients.contains(eventPacket.getClient())) return;
        clients.add(eventPacket.getClient());
        System.out.println("Client connected: " + eventPacket.getClient().hashCode());
    }

    @Override
    public void onDisconnect(EventPacket eventPacket) {
        if (!clients.contains(eventPacket.getClient())) return;
        clients.remove(eventPacket.getClient());
        broadcast(new PacketDisconnect());
        System.out.println("Client disconnected: " + eventPacket.getClient().hashCode());
    }

    @Override
    public void onPacketReceived(DClient client, Packet packet) {
        broadcast(packet.toRawPacket());
        if(packet instanceof PacketSetInfo){
            PacketSetInfo packetSetInfo = (PacketSetInfo) packet;
            System.out.printf("%s: Connected\n", packetSetInfo.getName());
        }
        if(packet instanceof PacketDisconnect){
            System.out.printf("%s: Disconnected\n", client.hashCode());
        }
        if(packet instanceof PacketMessage){
            PacketMessage packetMessage = (PacketMessage) packet;
            System.out.printf("%s: %s\n", client.hashCode(), packetMessage.getMessage());
        }
    }
    
}
