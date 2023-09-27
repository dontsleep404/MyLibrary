package test.server;

import me.dontsleep404.customsocket.DServer;

public class Server {
    public static void main(String[] args) {
        int port = 25565;
        
        DServer server = new DServer(port);
        server.setEventHandle(new ServerHandle());
        if (server.listen()){
            System.out.println("Server is listening on port " + port);
        } else {
            System.out.println("Server failed to listen on port " + port);
        }
    }
}
