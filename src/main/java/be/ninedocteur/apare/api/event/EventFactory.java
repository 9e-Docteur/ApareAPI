package be.ninedocteur.apare.api.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


public class EventFactory {
    private Map<Class<? extends Event>, List<Consumer<Event>>> listeners;

    public EventFactory() {
        listeners = new HashMap<>();
    }

    public <T extends Event> void addListener(Consumer<T> listener) {
        List<Consumer<Event>> eventListeners = getListeners((Class<? extends Event>) listener.getClass());
        if (eventListeners != null) {
            eventListeners.add((Consumer<Event>) listener);
        }
    }

    public void fireEvent(Event event) {
        List<Consumer<Event>> eventListeners = getListeners(event.getClass());
        eventListeners.forEach(listener -> listener.accept(event));
    }

    private List<Consumer<Event>> getListeners(Class<? extends Event> eventClass) {
        List<Consumer<Event>> eventListeners = listeners.get(eventClass);
        if (eventListeners == null) {
            eventListeners = new ArrayList<>();
            listeners.put(eventClass, eventListeners);
        }
        return eventListeners;
    }
}