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
    private Map<Class<? extends Event>, List<Method>> eventHandlers;

    public EventFactory() {
        eventHandlers = new HashMap<>();
    }

    public void addEvent(Object listener) {
        for (Method method : listener.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(ApareEventHandler.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 1 && Event.class.isAssignableFrom(parameterTypes[0])) {
                    Class<? extends Event> eventType = parameterTypes[0].asSubclass(Event.class);
                    eventHandlers.computeIfAbsent(eventType, key -> new ArrayList<>()).add(method);
                }
            }
        }
    }

    public void fireEvent(Event event) {
        List<Method> handlers = eventHandlers.get(event.getClass());
        if (handlers != null) {
            for (Method method : handlers) {
                try {
                    method.invoke(null, event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }}