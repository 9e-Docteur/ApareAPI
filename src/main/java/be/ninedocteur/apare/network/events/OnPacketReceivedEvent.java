package be.ninedocteur.apare.network.events;

import be.ninedocteur.apare.api.event.Event;
import be.ninedocteur.apare.network.Packet;

public class OnPacketReceivedEvent extends Event {
    private Packet packet;

    public OnPacketReceivedEvent(Packet packet){
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }
}
