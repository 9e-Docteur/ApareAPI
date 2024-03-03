package be.ninedocteur.apare.network;


import be.ninedocteur.apare.ApareAPI;
import be.ninedocteur.apare.network.events.OnPacketReceivedEvent;
import be.ninedocteur.apare.server.ServerConnection;
import be.ninedocteur.apare.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class PacketHandler {
    private static List<Class<? extends Packet>> packets = new ArrayList<>();

    public static List<Class<? extends Packet>> getPackets() {
        return packets;
    }

    public static void registerPacket(Class<? extends Packet> clazz){
        packets.add(clazz);
    }

    public void onReceived(Object p, ServerConnection connection) {
        //TO PROTECT UNKNOWN PACKETS
        if(!ApareAPI.getJavaArgs().containsArg("noPackets")){
            if (p instanceof Packet) {
                Packet packet = (Packet) p;
                OnPacketReceivedEvent onPacketReceivedEvent = new OnPacketReceivedEvent(packet);
                ApareAPI.getEventFactory().fireEvent(onPacketReceivedEvent);
                if (packets.contains(p.getClass())) {
                    packet.loadContent(packet.getPacketContent());
                    packet.execute(connection);
                } else {
                    System.out.println("Received unknown packet");
                }
            }
        } else {
            ApareAPI.getLogger().send("Received a packet, but ignored because --noPackets is in the program arguments", Logger.Type.ERROR);
        }

    }
}
