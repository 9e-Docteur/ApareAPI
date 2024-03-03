package be.ninedocteur.apare.server;

import be.ninedocteur.apare.api.mod.ModLoader;
import be.ninedocteur.apare.client.ClientConnection;
import be.ninedocteur.apare.utils.ApareAPIJVMArgs;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApareAPIServer implements Runnable{
    private int port;
    private ServerSocket serverSocket;
    private boolean running = false;
    private int id = 0;
    public HashMap<Integer, ServerConnection> connections = new HashMap<Integer, ServerConnection>();
    private List<ClientConnection> clients = new ArrayList<>();
    private ModLoader modLoader;
    private static ApareAPIJVMArgs javaArgs;

    public ApareAPIServer(String[] args, int port){
        javaArgs = new ApareAPIJVMArgs(args);
        this.port = port;
        if(!javaArgs.containsArg("noMods")){
            modLoader = new ModLoader();
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
        new Thread(connection).start();
        id++;
    }

    private void shutdown() {
        running = false;

        try{
            serverSocket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
