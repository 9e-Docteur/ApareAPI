package be.ninedocteur.apare;

import be.ninedocteur.apare.api.mod.ModLoader;
import be.ninedocteur.apare.utils.Logger;

public class ApareAPI {
    public static boolean isStarted;
    private static Logger logger = new Logger();

    public static void main(String[] args) {
        start();
    }

    public static void start(){
        if(!isStarted){
            ModLoader modLoader = new ModLoader();
            logger.send("Starting ApareAPI", Logger.Type.WARN);
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
