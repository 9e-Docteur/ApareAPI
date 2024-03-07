package be.ninedocteur.apare.server;

import be.ninedocteur.apare.AIN;
import be.ninedocteur.apare.ApareAPI;
import be.ninedocteur.apare.api.mod.ModLoader;
import be.ninedocteur.apare.api.mod.ModSide;
import be.ninedocteur.apare.network.PacketContent;
import be.ninedocteur.apare.network.events.ClientJoiningServerEvent;
import be.ninedocteur.apare.utils.ApareAPIJVMArgs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ApareAPIServer implements Runnable{
    private int port;
    private ServerSocket serverSocket;
    private boolean running = false;
    private int id = 0;
    public HashMap<Integer, ServerConnection> connections = new HashMap<Integer, ServerConnection>();
    public HashMap<Integer, PacketContent> informations_for_connection_id = new HashMap<Integer, PacketContent>();
    private ModLoader modLoader;

    public ApareAPIServer(String[] args, int port){
        AIN.init(args);
        this.port = port;
        if(!AIN.getJavaArgs().containsArg("noMods")){
            modLoader = new ModLoader(ModSide.SERVER);
            modLoader.loadMods();
        }
        try{
            serverSocket = new ServerSocket(port);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void startServer(){
        new Thread(this).start();
    }

    @Override
    public void run() {
        running = true;
        System.out.println("Server started on port: " + port);

        while(running){
            try{
                Socket socket = serverSocket.accept();
                initSocket(socket);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        shutdown();
    }

    private void initSocket(Socket socket) {
        ServerConnection connection = new ServerConnection(socket, id);
        connections.put(id, connection);
        ClientJoiningServerEvent clientJoiningServerEvent = new ClientJoiningServerEvent(id, connection);
        ApareAPI.getEventFactory().fireEvent(clientJoiningServerEvent);
        new Thread(connection).start();
        id++;
    }

    public void shutdown() {
        running = false;

        try{
            serverSocket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
