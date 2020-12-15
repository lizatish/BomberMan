package ru.rsreu.tishkovets.events;

import ru.rsreu.tishkovets.events.data.EventData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager implements Serializable {

    public void subscribe(EventType eventType, EventListener listener) {
        eventType.subscribe(listener);
    }

    public synchronized void notify(EventType eventType, EventData data) {
        List<EventListener> users = eventType.getListeners();
        for (EventListener listener : users) {
            listener.update(data);
        }
    }
}
