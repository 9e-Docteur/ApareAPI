package be.ninedocteur.apare.api.mod.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApareRegistry<E> {
    private List<ApareRegistryObject<E>> items;
    private RegistryType registryType;
    private String modId;

    private ApareRegistry(RegistryType registryType, String modId) {
        this.items = new ArrayList<>();
        this.registryType = registryType;
        this.modId = modId;
    }

    public static <E> ApareRegistry<E> create(RegistryType registryType, String modId) {
        return new ApareRegistry<>(registryType, modId);
    }

    public ApareRegistryObject<E> register(E item) {
        ApareRegistryObject<E> newObject = new ApareRegistryObject<>(item);
        items.add(newObject);
        return newObject;
    }

    public void registerToApareDriverList(HashMap<Object, String> hash){
            for(ApareRegistryObject<E> content : getRegisteredContents()){
                hash.put(content.get(), getModId());
            }
    }

    public List<ApareRegistryObject<E>> getRegisteredContents() {
        return items;
    }

    public RegistryType getRegistryType() {
        return registryType;
    }

    public String getModId() {
        return modId;
    }
}
