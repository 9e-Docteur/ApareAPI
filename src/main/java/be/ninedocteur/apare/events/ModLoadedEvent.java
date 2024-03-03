package be.ninedocteur.apare.events;

import be.ninedocteur.apare.api.event.Event;
import be.ninedocteur.apare.api.mod.ApareMod;

public class ModLoadedEvent extends Event {
    private ApareMod mod;

    public ModLoadedEvent(ApareMod mod){
        this.mod = mod;
    }

    public ApareMod getMod() {
        return mod;
    }
}
