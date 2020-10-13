package ru.rsreu.tishkovets.events;

import ru.rsreu.tishkovets.events.data.EventData;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private List<EventListener> listeners;

    public EventManager() {
        listeners = new ArrayList<>();
    }

    public void subscribe(EventListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(EventListener listener) {
        listeners.remove(listener);
    }

    public void notifyAllListeners(EventData data) {
        for (EventListener listener : listeners) {
            listener.update(data);
        }
    }
}
