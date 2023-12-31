package test.packet;

import me.dontsleep404.customsocket.packet.Packet;

public class PacketMessage extends Packet{
    public enum Type{
        MESSAGE,
        FILE
    }
    String name;
    String message;
    Type type;
    String fileName;
    byte[] fileData;
    public PacketMessage(String message, String name) {
        this.message = message;
        this.type = Type.MESSAGE;
        this.name = name;
    }
    public PacketMessage(String fileName, byte[] fileData) {
        this.fileName = fileName;
        this.fileData = fileData;
        this.type = Type.FILE;
    }
    public String getMessage(){
        return message;
    }
    public Type getType(){
        return type;
    }
    public String getFileName(){
        return fileName;
    }
    public byte[] getFileData(){
        return fileData;
    }
    public String getName(){
        return name;
    }
}


