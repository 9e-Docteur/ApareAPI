package be.ninedocteur.apare.network.events;

import be.ninedocteur.apare.api.event.Event;
import be.ninedocteur.apare.server.ServerConnection;

public class ClientJoiningServerEvent extends Event {
    public int id;
    public ServerConnection connection;

    public ClientJoiningServerEvent(int id, ServerConnection serverConnection){
        this.id = id;
        this.connection = serverConnection;
    }

    public int getId() {
        return id;
    }

    public ServerConnection getConnection() {
        return connection;
    }
}
