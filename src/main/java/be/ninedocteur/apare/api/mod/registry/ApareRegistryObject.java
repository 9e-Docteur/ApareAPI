package be.ninedocteur.apare.api.mod.registry;

public class ApareRegistryObject<E> {
    private E object;

    public ApareRegistryObject(E object) {
        this.object = object;
    }

    public E get() {
        return object;
    }
}
