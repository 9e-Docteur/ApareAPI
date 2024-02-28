package be.ninedocteur.apare.client;


import be.ninedocteur.apare.ApareAPI;
import be.ninedocteur.apare.network.Packet;
import be.ninedocteur.apare.network.PacketContent;
import be.ninedocteur.apare.network.PacketHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection implements Runnable{
    private String host;
    private int port;

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public int id;
    private PacketHandler packetHandler;
    private boolean running = false;

    public ClientConnection(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void connect(){
        try{
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            packetHandler = ApareAPI.getPacketHandler();
            new Thread(this).start();
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
                    packetHandler.onReceived(data, null); //CLIENT CONNECTION, NOT SERVER
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

    //SEND PACKET TO SERVER
    public void sendPacket(Packet packet) { //TODO:REPLACE PACKET WITH OBJECT IF NOT WORKING
        try {
            packet.setPacketContent(new PacketContent());
            packet.saveContent(packet.getPacketContent());
            out.writeObject(packet);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
