package be.ninedocteur.apare.api.mod.registery;

public class ApareRegisteryObject<E> {
    private E object;

    public ApareRegisteryObject(E object) {
        this.object = object;
    }

    public E get() {
        return object;
    }
}
