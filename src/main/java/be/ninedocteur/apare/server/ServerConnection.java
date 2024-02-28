package be.ninedocteur.apare.server;

import be.ninedocteur.apare.ApareAPI;
import be.ninedocteur.apare.network.Packet;
import be.ninedocteur.apare.network.PacketContent;
import be.ninedocteur.apare.network.PacketHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnection implements Runnable{
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public int id;
    private PacketHandler packetHandler;
    private boolean running = false;

    public ServerConnection(Socket socket, int id){
        this.socket = socket;
        this.id = id;

        try{
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            packetHandler = ApareAPI.getPacketHandler();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            running = true;

            while(running){
                try {
                    Object data = in.readObject();
                    packetHandler.onReceived(data, this);
                } catch (ClassNotFoundException e){
                    e.printStackTrace();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            running = false;
            in.close();
            out.close();
            socket.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    //SEND PACKET TO PLAYER
    public void sendPacket(Packet packet) { //TODO:REPLACE PACKET WITH OBJECT IF NOT WORKING
        try {
            packet.setPacketContent(new PacketContent());
            packet.saveContent(packet.getPacketContent());
            out.writeObject(packet);
            out.flush();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
