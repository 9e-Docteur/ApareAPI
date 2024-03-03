package be.ninedocteur.apare;

import be.ninedocteur.apare.api.event.EventFactory;
import be.ninedocteur.apare.api.mod.ModLoader;
import be.ninedocteur.apare.events.APIStartingEvent;
import be.ninedocteur.apare.network.PacketHandler;
import be.ninedocteur.apare.utils.ApareAPIJVMArgs;
import be.ninedocteur.apare.utils.Logger;
import be.ninedocteur.apare.utils.tick.ITicker;
import be.ninedocteur.apare.utils.tick.TickerManager;

import java.util.*;

public class ApareAPI {
    public static boolean isStarted;
    private static Logger logger = new Logger();
    private static HashMap<String, Thread> RUNNING_THREAD = new HashMap<>();
    private static EventFactory eventFactory;
    private static PacketHandler packetHandler;
    private static ApareAPIJVMArgs javaArgs;
    public static List<ITicker> CLASSES_TO_TICK = new ArrayList<>();

    public static void main(String[] args) {
        javaArgs = new ApareAPIJVMArgs(args);
        start();
        while(true){
            for(Map.Entry<String, Thread> entry : RUNNING_THREAD.entrySet()){
                if(!entry.getValue().isAlive()){
                    entry.getValue().run();
                    entry.getValue().stop();
                    RUNNING_THREAD.remove(entry.getKey());
                }
            }
            for(ITicker ticker : CLASSES_TO_TICK){
                ticker.tick();
            }
        }
    }

    public static void start(){
        if(!isStarted){
            logger.send("Starting ApareAPI...", Logger.Type.WARN);
            eventFactory = new EventFactory();
            packetHandler = new PacketHandler();
            if(!javaArgs.containsArg("noMods")){
                ModLoader modLoader = new ModLoader();
            }
            TickerManager.start();
            APIStartingEvent apiStartingEvent = new APIStartingEvent();
            eventFactory.fireEvent(apiStartingEvent);
        }
    }

    public static void doAction(Runnable runnable){
        if(javaArgs.containsArg("singleThread")){
            runnable.run();
        } else {
            //For now bc string is not needed so hashmap -> list
            Random random = new Random();
            byte[] by = "RandomString".getBytes();
            random.nextBytes(by);
            String name = new String(by);
            Thread newThread = new Thread(runnable);
            newThread.run();
            RUNNING_THREAD.put(name, newThread);
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
