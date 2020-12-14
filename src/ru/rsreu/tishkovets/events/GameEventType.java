package ru.rsreu.tishkovets.events;

import ru.rsreu.tishkovets.model.GameAction;
import ru.rsreu.tishkovets.model.Serializator;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum GameEventType {
    START(Collections.singletonList(KeyEvent.getKeyText(KeyEvent.VK_ENTER).toUpperCase())) {
        @Override
        public void startAction(GameAction game) {
            game.start();
        }
    },
    PAUSE(Arrays.asList(KeyEvent.getKeyText(KeyEvent.VK_P).toUpperCase(),
            KeyEvent.getKeyText(KeyEvent.VK_O).toUpperCase())) {
        @Override
        public void startAction(GameAction game) {
            game.pause(!game.isPaused());
        }
    },
    BOMB(Arrays.asList(KeyEvent.getKeyText(KeyEvent.VK_SPACE).toUpperCase(),
            KeyEvent.getKeyText(KeyEvent.VK_ALT).toUpperCase())) {
        @Override
        public void startAction(GameAction game) {
            game.placeBomb();
        }
    },
    SAVE_GAME(Arrays.asList(KeyEvent.getKeyText(KeyEvent.VK_F1).toUpperCase())) {
        @Override
        public void startAction(GameAction game) {
            serializator.saveState(game);
        }
    },
    LOAD_GAME(Arrays.asList(KeyEvent.getKeyText(KeyEvent.VK_F2).toUpperCase())) {
        @Override
        public void startAction(GameAction game) {
            game.start();
        }
    };

    private final List<String> keyNames;
    protected final Serializator serializator = new Serializator();

    GameEventType(List<String> keyNames) {
        this.keyNames = keyNames;
    }


    public static GameEventType getGameEventTypeByKeyName(List<String> keyNames) {

        GameEventType result = null;
        if (!keyNames.isEmpty()) {
            String keyName = keyNames.get(0);
            for (GameEventType eventType : GameEventType.values()) {
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

    public abstract void startAction(GameAction game);
}
