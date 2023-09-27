package test.server;
import java.util.ArrayList;
import java.util.HashMap;

import me.dontsleep404.customsocket.DClient;
import me.dontsleep404.customsocket.event.EventHandle;
import me.dontsleep404.customsocket.event.EventPacket;
import me.dontsleep404.customsocket.packet.Packet;
public class ServerHandle extends EventHandle{

    public ArrayList<DClient> clients = new ArrayList<DClient>();

    public ServerHandle() {
        super(new HashMap<String, Class<? extends Packet>>(){

        });
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
        System.out.println("Client disconnected: " + eventPacket.getClient().hashCode());
    }

    @Override
    public void onPacketReceived(EventPacket eventPacket) {
        
    }

    @Override
    public void onSentPacket(EventPacket eventPacket) {
        
    }
    
}
