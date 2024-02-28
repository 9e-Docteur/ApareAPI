package be.ninedocteur.apare.network;


import be.ninedocteur.apare.server.ServerConnection;

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
        if (p instanceof Packet) {
            Packet packet = (Packet) p;
            if (packets.contains(p.getClass())) {
                packet.loadContent(packet.getPacketContent());
                packet.execute(connection);
            } else {
                System.out.println("Received unknown packet");
            }
        }

    }
}
