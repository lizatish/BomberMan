package ru.rsreu.tishkovets.events;

import ru.rsreu.tishkovets.events.data.EventData;

public interface EventListener {
    void update(EventData data);
}
