package ru.rsreu.tishkovets.events;

public interface Subscriber {
    void subscribe(EventType eventType, EventListener listener);

    void unsubscribe(EventType eventType, EventListener listener);
}
