package test.client;

import java.util.HashMap;

import me.dontsleep404.customsocket.DClient;
import me.dontsleep404.customsocket.event.EventHandle;
import me.dontsleep404.customsocket.event.EventPacket;
import me.dontsleep404.customsocket.packet.Packet;
import test.packet.PacketConnect;
import test.packet.PacketDisconnect;
import test.packet.PacketMessage;
import test.packet.PacketSetInfo;

public class ClientHandle extends EventHandle{

    public HashMap<DClient, String> clients = new HashMap<DClient, String>();

    public ClientHandle() {
        super(new HashMap<String, Class<? extends Packet>>(){
            {
                put("PacketMessage", PacketMessage.class);
                put("PacketSetInfo", PacketSetInfo.class);
                put("PacketConnect", PacketConnect.class);
                put("PacketDisconnect", PacketDisconnect.class);
            }
        });
    }

    @Override
    public void onConnect(EventPacket eventPacket) {
        System.out.println("Connected to server");
    }

    @Override
    public void onDisconnect(EventPacket eventPacket) {
        System.out.println("Disconnected from server");
    }

    @Override
    public void onPacketReceived(EventPacket eventPacket, Class<? extends Packet> packetClass) {
        Packet packet = eventPacket.getPacket().toPacket(packetClass);
        if(packet instanceof PacketSetInfo){
            PacketSetInfo packetSetInfo = (PacketSetInfo) packet;
            clients.put(eventPacket.getClient(), packetSetInfo.getName());
            System.out.printf("%s: Connected\n", packetSetInfo.getName());
        }
        if(packet instanceof PacketDisconnect){
            String name = clients.get(eventPacket.getClient());
            System.out.printf("%s: Disconnected\n", name);
            clients.remove(eventPacket.getClient());
        }
        if(packet instanceof PacketMessage){
            PacketMessage packetMessage = (PacketMessage) packet;
            System.out.printf("%s: %s\n", clients.get(eventPacket.getClient()), packetMessage.getMessage());
        }
    }

    @Override
    public void onSentPacket(EventPacket eventPacket, Class<? extends Packet> packetClass) {
        eventPacket.getClient().sendRawPacket(eventPacket.getPacket());
    }
    
}
