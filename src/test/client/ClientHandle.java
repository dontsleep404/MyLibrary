package test.client;

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

public class ClientHandle extends EventHandle{

    public HashMap<DClient, String> clients = new HashMap<DClient, String>();

    public ClientHandle() {
        super(new ArrayList<Class<? extends Packet>>(){
            {
                add(PacketMessage.class);
                add(PacketSetInfo.class);
                add(PacketConnect.class);
                add(PacketDisconnect.class);
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
    public void onPacketReceived(DClient client, Packet packet) {
        if(packet instanceof PacketSetInfo){
            PacketSetInfo packetSetInfo = (PacketSetInfo) packet;
            clients.put(client, packetSetInfo.getName());
            System.out.printf("%s: Connected\n", packetSetInfo.getName());
        }
        if(packet instanceof PacketDisconnect){
            String name = clients.get(client);
            System.out.printf("%s: Disconnected\n", name);
            clients.remove(client);
        }
        if(packet instanceof PacketMessage){
            PacketMessage packetMessage = (PacketMessage) packet;
            System.out.printf("%s: %s\n", clients.get(client), packetMessage.getMessage());
        }
    }

    
}
