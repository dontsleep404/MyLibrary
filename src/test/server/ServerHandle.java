package test.server;
import java.util.HashMap;

import me.dontsleep404.customsocket.event.EventHandle;
import me.dontsleep404.customsocket.event.EventPacket;
import me.dontsleep404.customsocket.packet.Packet;
public class ServerHandle extends EventHandle{

    

    public ServerHandle() {
        super(new HashMap<String, Class<? extends Packet>>(){

        });
    }

    @Override
    public void onConnect(EventPacket eventPacket) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onConnect'");
    }

    @Override
    public void onDisconnect(EventPacket eventPacket) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onDisconnect'");
    }

    @Override
    public void onPacketReceived(EventPacket eventPacket) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onPacketReceived'");
    }

    @Override
    public void onSentPacket(EventPacket eventPacket) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onSentPacket'");
    }
    
}
