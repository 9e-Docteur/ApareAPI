package be.ninedocteur.apare.api.mod.registery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApareRegistery<E> {
    private List<ApareRegisteryObject<E>> items;
    private RegisteryType registryType;
    private String modId;

    private ApareRegistery(RegisteryType registryType, String modId) {
        this.items = new ArrayList<>();
        this.registryType = registryType;
        this.modId = modId;
    }

    public static <E> ApareRegistery<E> create(RegisteryType registryType, String modId) {
        return new ApareRegistery<>(registryType, modId);
    }

    public ApareRegisteryObject<E> register(E item) {
        ApareRegisteryObject<E> newObject = new ApareRegisteryObject<>(item);
        items.add(newObject);
        return newObject;
    }

    public void registerToApareDriverList(HashMap<Object, String> hash){
            for(ApareRegisteryObject<E> content : getRegisteredContents()){
                hash.put(content.get(), getModId());
            }
    }

    public List<ApareRegisteryObject<E>> getRegisteredContents() {
        return items;
    }

    public RegisteryType getRegistryType() {
        return registryType;
    }

    public String getModId() {
        return modId;
    }
}
