package be.ninedocteur.apare.api;

import be.ninedocteur.apare.api.mod.registry.ApareRegistry;

import java.util.HashMap;

public class ApareDriver {
    private HashMap<String, String> REGISTERED_TO_DRIVER = new HashMap<>();

    public void registerToApareDriver(ApareRegistry apareRegistery){
        apareRegistery.registerToApareDriverList(REGISTERED_TO_DRIVER);
    }

    public HashMap<String, String> getRegisteredToDriver() {
        return REGISTERED_TO_DRIVER;
    }
}
