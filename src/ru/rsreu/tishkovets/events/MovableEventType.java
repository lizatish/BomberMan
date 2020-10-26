package ru.rsreu.tishkovets.events;


import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

public enum MovableEventType {
    UP(Arrays.asList(KeyEvent.getKeyText(KeyEvent.VK_UP).toUpperCase(),
            KeyEvent.getKeyText(KeyEvent.VK_W).toUpperCase())) {
    },
    LEFT(Arrays.asList(KeyEvent.getKeyText(KeyEvent.VK_LEFT).toUpperCase(),
            KeyEvent.getKeyText(KeyEvent.VK_A).toUpperCase())) {
    },
    DOWN(Arrays.asList(KeyEvent.getKeyText(KeyEvent.VK_DOWN).toUpperCase(),
            KeyEvent.getKeyText(KeyEvent.VK_S).toUpperCase())) {
    },
    RIGHT(Arrays.asList(KeyEvent.getKeyText(KeyEvent.VK_RIGHT).toUpperCase(),
            KeyEvent.getKeyText(KeyEvent.VK_D).toUpperCase())) {
    };

    private final List<String> keyNames;

    MovableEventType(List<String> keyNames) {
        this.keyNames = keyNames;
    }

    public static MovableEventType getMovableOperationByKeyName(List<String> keyNames) {

        MovableEventType result = null;
        if (!keyNames.isEmpty()) {
            String keyName = keyNames.get(0);
            for (MovableEventType eventType : MovableEventType.values()) {
                if (eventType.getKeyName().contains(keyName)) {
                    result = eventType;
                    break;
                }
            }
        }
        return result;
    }

    public List<String> getKeyName() {
        return keyNames;
    }

}
