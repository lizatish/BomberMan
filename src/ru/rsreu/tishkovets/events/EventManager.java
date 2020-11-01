package ru.rsreu.tishkovets.events;

import ru.rsreu.tishkovets.events.data.EventData;
import ru.rsreu.tishkovets.events.data.ModelUpdateEventData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager implements Subscriber {
    private final Map<String, List<EventListener>> listeners = new HashMap<String, List<EventListener>>();

    public EventManager() {
        for (EventType operation : EventType.values()) {
            this.listeners.put(operation.name(), new ArrayList());
        }
    }

    public void subscribe(EventType eventType, EventListener listener) {
//        eventType.setListeners((List<EventListener>) listener);
        List<EventListener> users = listeners.get(eventType.name());
        users.add(listener);
    }

    public void unsubscribe(EventType eventType, EventListener listener) {
        List<EventListener> users = listeners.get(eventType.name());
        users.remove(listener);
    }

    public void notify(EventType eventType, EventData data) {
        List<EventListener> users = listeners.get(eventType.name());
        for (EventListener listener : users) {
            listener.update(data);
        }
    }
}
