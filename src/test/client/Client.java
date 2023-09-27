package test.client;

import java.util.Scanner;

import me.dontsleep404.customsocket.DClient;
import test.packet.PacketMessage;
import test.packet.PacketSetInfo;

public class Client {
    public static void main(String[] args) {
        String name = args[0];
        DClient client = new DClient("localhost", 25565);
        client.setEventHandle(new ClientHandle());
        if(client.connect()){
            client.listen();
            PacketSetInfo packet = new PacketSetInfo(name);
            client.sendPacket(packet);
        }else{
            System.out.println("Failed to connect to server");
        }
        Scanner sc = new Scanner(System.in);
        while(true){
            String msg = sc.nextLine();
            if(msg.equalsIgnoreCase("exit")){
                client.disconnect();
                break;
            }
            PacketMessage packet = new PacketMessage(msg);
            client.sendPacket(packet);            
        }
        sc.close();
    }
}
