package be.ninedocteur.apare.network;

import be.ninedocteur.apare.server.ServerConnection;

import java.io.Serializable;

public abstract class Packet implements Serializable {
    private static final long serialVersionUID = 1L;
    private PacketContent packetContent;


    public abstract void saveContent(PacketContent packetContent); //WHAT TO SAVE IN THE PACKET (like string or an int or other) TO THE RECEIVER
    public abstract void loadContent(PacketContent packetContent); //THE RECEIVER WILL LOAD / GET EVERY SAVED CONTENT IN THE PACKET

    public abstract void execute(ServerConnection connection); //WILL BE EXECUTED ON THE TARGET RECEIVER

    public void setPacketContent(PacketContent packetContent) {
        this.packetContent = packetContent;
    } //ONLY WHEN SENDING PACKET !!!!!!!

    public PacketContent getPacketContent() {
        return packetContent;
    }
}
