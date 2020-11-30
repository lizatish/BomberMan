package ru.rsreu.tishkovets.events;

import java.util.*;

public enum EventType {
    INIT_UPDATE {

    },
    MAINHERO_UPDATE {

    },
    ENEMY_UPDATE {

    },
    PLACE_BOMB {

    },
    EXPLOSION_UPDATE {

    },
    BOX_DELETE {

    };


    public void subscribe(EventListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        this.listeners.add(listener);
    }

    private List<EventListener> listeners;

    public List<EventListener> getListeners() {
        return listeners;
    }
}
