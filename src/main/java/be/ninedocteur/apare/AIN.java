package be.ninedocteur.apare;

import be.ninedocteur.apare.api.ApareDriver;
import be.ninedocteur.apare.api.event.ApareEventHandler;
import be.ninedocteur.apare.api.event.EventFactory;
import be.ninedocteur.apare.events.APIStartingEvent;
import be.ninedocteur.apare.network.PacketHandler;
import be.ninedocteur.apare.utils.ApareAPIJVMArgs;
import be.ninedocteur.apare.utils.DevicesInfos;
import be.ninedocteur.apare.utils.logger.Logger;
import be.ninedocteur.apare.utils.tick.ITicker;
import be.ninedocteur.apare.utils.tick.TickerManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//STANDS FOR ApareAPI Ingnition system
public class AIN {
    private static List<ITicker> CLASSES_TO_TICK = new ArrayList<>();
    private static HashMap<String, Thread> RUNNING_THREAD = new HashMap<>();
    private static EventFactory eventFactory;
    private static PacketHandler packetHandler;
    private static ApareAPIJVMArgs javaArgs;

    private static ApareDriver apareDriver;
    static boolean ignited;
    private static boolean ticking;
    
    public static void init(String[] args){
        if(!ignited){
            eventFactory = new EventFactory();
            packetHandler = new PacketHandler();
            apareDriver = new ApareDriver();
            javaArgs = new ApareAPIJVMArgs(args);
            eventFactory.addListener(AIN::testEvent);
            APIStartingEvent apiStartingEvent = new APIStartingEvent();
            eventFactory.fireEvent(apiStartingEvent);
            TickerManager.start(CLASSES_TO_TICK);
            ApareAPI.getLogger().send("Running on: " + DevicesInfos.getOSName() + " --> " + DevicesInfos.getProcessorArch(), Logger.Type.NORMAL);
            ApareAPI.getLogger().send("Memory usage: " + DevicesInfos.getOccupiedMemory() + "MB/" + DevicesInfos.getFreeMemory() + "MB", Logger.Type.NORMAL);
            ApareAPI.getLogger().send("Started ApareAPI !", Logger.Type.SUCCESS);
            ignited = true;
        }
    }

    @ApareEventHandler
    public static void testEvent(APIStartingEvent event){
        ApareAPI.getLogger().send("Started!", Logger.Type.SUCCESS);
    }
    
    public static void tick(){
        if(!ticking){
            ticking = true;
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
            ticking = false;
        }
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

    public static ApareDriver getDriver() {
        return apareDriver;
    }

    public static HashMap<String, Thread> getRunningThread() {
        return RUNNING_THREAD;
    }
}
