package ru.rsreu.tishkovets.events;

import java.util.*;

public enum EventType {
    INIT_UPDATE {
        public List<EventListener> getListeners() {
            return listeners;
        }

        public void subscribe(EventListener listener) {
            if (listeners == null) {
                listeners = new ArrayList<>();
            }
            this.listeners.add(listener);
        }

        private List<EventListener> listeners;
    },
    MAINHERO_UPDATE {
        public List<EventListener> getListeners() {
            return listeners;
        }

        public void subscribe(EventListener listener) {
            if (listeners == null) {
                listeners = new ArrayList<>();
            }
            this.listeners.add(listener);
        }

        private List<EventListener> listeners;
    },
    ENEMY_UPDATE {
        public List<EventListener> getListeners() {
            return listeners;
        }

        public void subscribe(EventListener listener) {
            if (listeners == null) {
                listeners = new ArrayList<>();
            }
            this.listeners.add(listener);
        }

        private List<EventListener> listeners;
    },
    BOMB_UPDATE {
        public List<EventListener> getListeners() {
            return listeners;
        }

        public void subscribe(EventListener listener) {
            if (listeners == null) {
                listeners = new ArrayList<>();
            }
            this.listeners.add(listener);
        }

        private List<EventListener> listeners;
    },
    EXPLOSION_UPDATE {
        public List<EventListener> getListeners() {
            return listeners;
        }

        public void subscribe(EventListener listener) {
            if (listeners == null) {
                listeners = new ArrayList<>();
            }
            this.listeners.add(listener);
        }

        private List<EventListener> listeners;
    };

    public abstract void subscribe(EventListener listeners);

    public abstract List<EventListener> getListeners();
}
