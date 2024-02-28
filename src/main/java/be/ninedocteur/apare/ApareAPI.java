package be.ninedocteur.apare;

import be.ninedocteur.apare.api.event.EventFactory;
import be.ninedocteur.apare.api.mod.ModLoader;
import be.ninedocteur.apare.events.APIStartingEvent;
import be.ninedocteur.apare.network.PacketHandler;
import be.ninedocteur.apare.utils.ApareAPIJVMArgs;
import be.ninedocteur.apare.utils.Logger;

public class ApareAPI {
    public static boolean isStarted;
    private static Logger logger = new Logger();
    private static EventFactory eventFactory;
    private static PacketHandler packetHandler;
    private static ApareAPIJVMArgs javaArgs;

    public static void main(String[] args) {
        javaArgs = new ApareAPIJVMArgs(args);
        start();
    }

    public static void start(){
        if(!isStarted){
            eventFactory = new EventFactory();
            packetHandler = new PacketHandler();
            ModLoader modLoader = new ModLoader();
            logger.send("Starting ApareAPI...", Logger.Type.WARN);
            APIStartingEvent apiStartingEvent = new APIStartingEvent();
            eventFactory.fireEvent(apiStartingEvent);
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    public static EventFactory getEventFactory() {
        return eventFactory;
    }

    public static PacketHandler getPacketHandler() {
        return packetHandler;
    }

    public static ApareAPIJVMArgs getJavaArgs() {
        return javaArgs;
    }
}
