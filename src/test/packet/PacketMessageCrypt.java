package test.packet;

import java.util.Base64;

public class PacketMessageCrypt extends PacketMessage{

    public PacketMessageCrypt(String message, String name) {
        super(message, name);
    }

    public void encryptWithKey(){
        message = toBase64(message);        
    }  

    public void decryptWithKey(){
        message = fromBase64(message);
    }

    public String toBase64(String message){
        return Base64.getEncoder().encodeToString(message.getBytes());
    } 

    public String fromBase64(String message){
        return new String(Base64.getDecoder().decode(message));
    }
}
