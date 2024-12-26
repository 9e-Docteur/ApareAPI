package be.ninedocteur.apare.events;

import be.ninedocteur.apare.api.event.Event;
import be.ninedocteur.apare.api.mod.ApareMod;
import be.ninedocteur.apare.api.mod.ModLoader;

public class ModLoadedEvent extends Event {
    private ModLoader modLoader;
    private ApareMod mod;

    public ModLoadedEvent(ModLoader modLoader, ApareMod mod){
        this.modLoader = modLoader;
        this.mod = mod;
    }

    public ApareMod getMod() {
        return mod;
    }

    public ModLoader getModLoader() {
        return modLoader;
    }
}
