package be.ninedocteur.apare;

import be.ninedocteur.apare.api.ApareDriver;
import be.ninedocteur.apare.api.event.EventFactory;
import be.ninedocteur.apare.api.mod.ModLoader;
import be.ninedocteur.apare.api.mod.ModSide;
import be.ninedocteur.apare.events.APIStartingEvent;
import be.ninedocteur.apare.network.PacketHandler;
import be.ninedocteur.apare.utils.ApareAPIJVMArgs;
import be.ninedocteur.apare.utils.DevicesInfos;
import be.ninedocteur.apare.utils.Logger;
import be.ninedocteur.apare.utils.tick.ITicker;
import be.ninedocteur.apare.utils.tick.TickerManager;

import java.util.*;

public class ApareAPI {
    public static boolean isStarted;
    private static Logger logger = new Logger();
    private static ModLoader modLoader;
    private static boolean modIgnited;

    public static void main(String[] args) {
        AIN.init(args);
        start();
        while (true){
            if(!modIgnited){
                if(AIN.ignited){
                    if(!getJavaArgs().containsArg("noMods")){
                        modLoader = new ModLoader(ModSide.CLIENT);
                        modLoader.loadMods();
                    }
                }
            }
        }
    }

    public static void start(){
        if(!isStarted){
            AIN.tick();
            logger.send("Starting ApareAPI...", Logger.Type.WARN);
        }
    }

    public static void doAction(Runnable runnable){
        if(getJavaArgs().containsArg("singleThread")){
            runnable.run();
        } else {
            //For now bc string is not needed so hashmap -> list
            Random random = new Random();
            byte[] by = "RandomString".getBytes();
            random.nextBytes(by);
            String name = new String(by);
            Thread newThread = new Thread(runnable);
            newThread.run();
            AIN.getRunningThread().put(name, newThread);
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    public static EventFactory getEventFactory() {
        return AIN.getEventFactory();
    }

    public static ApareAPIJVMArgs getJavaArgs() {
        return AIN.getJavaArgs();
    }

    public static PacketHandler getPacketHandler() {
        return AIN.getPacketHandler();
    }
}
