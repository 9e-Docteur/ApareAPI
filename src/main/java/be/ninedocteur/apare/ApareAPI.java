package be.ninedocteur.apare;

import be.ninedocteur.apare.api.event.EventFactory;
import be.ninedocteur.apare.api.mod.ModLoader;
import be.ninedocteur.apare.events.APIStartingEvent;
import be.ninedocteur.apare.utils.Logger;

public class ApareAPI {
    public static boolean isStarted;
    private static Logger logger = new Logger();
    private static EventFactory eventFactory;

    public static void main(String[] args) {
        start();
    }

    public static void start(){
        if(!isStarted){
            eventFactory = new EventFactory();
            APIStartingEvent apiStartingEvent = new APIStartingEvent();
            eventFactory.addEvent(apiStartingEvent);
            eventFactory.fireEvent(apiStartingEvent);
            ModLoader modLoader = new ModLoader();
            logger.send("Starting ApareAPI...", Logger.Type.WARN);
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    public static EventFactory getEventFactory() {
        return eventFactory;
    }
}
