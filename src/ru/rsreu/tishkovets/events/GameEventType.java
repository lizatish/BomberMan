package ru.rsreu.tishkovets.events;

import ru.rsreu.tishkovets.model.GameAction;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum GameEventType {
    START(Collections.singletonList(KeyEvent.VK_ENTER)) {
        @Override
        public void startAction(GameAction game) {
            game.start();
        }
    },
    PAUSE(Arrays.asList(KeyEvent.VK_P, KeyEvent.VK_O)) {
        @Override
        public void startAction(GameAction game) {
            game.pause(!game.isPaused());
        }
    },
    BOMB(Arrays.asList(KeyEvent.VK_SPACE, KeyEvent.VK_ALT)) {
        @Override
        public void startAction(GameAction game) {
            game.placeBomb();
        }
    };

    private final List<Integer> keyCodes;

    GameEventType(List<Integer> keyCode) {
        this.keyCodes = keyCode;
    }

    public static GameEventType getGameEventByKeyCode(int keyCode) {
        GameEventType result = null;
        for (GameEventType eventType : GameEventType.values()) {
            if (eventType.getKeyCode().contains(keyCode)) {
                result = eventType;
                break;
            }
        }
        return result;
    }

    public List<Integer> getKeyCode() {
        return keyCodes;
    }

    public abstract void startAction(GameAction game);
}
