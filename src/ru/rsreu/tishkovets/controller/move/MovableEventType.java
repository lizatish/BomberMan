package ru.rsreu.tishkovets.controller.move;


import ru.rsreu.tishkovets.Settings;
import ru.rsreu.tishkovets.model.gameobjects.Box;
import ru.rsreu.tishkovets.model.gameobjects.MainHero;
import ru.rsreu.tishkovets.model.gameobjects.Wall;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum MovableEventType {
    UP(Arrays.asList(KeyEvent.getKeyText(KeyEvent.VK_UP).toUpperCase(),
            KeyEvent.getKeyText(KeyEvent.VK_W).toUpperCase())) {
        @Override
        public void startMoving(Movable entity) {
            entity.moveUp();
        }

        @Override
        public boolean canMove(List<Wall> walls, List<Box> boxes, MainHero mainHero) {
            if (mainHero.getPositionY() == 0) {
                return false;
            }

            return true;
        }
    },
    LEFT(Arrays.asList(KeyEvent.getKeyText(KeyEvent.VK_LEFT).toUpperCase(),
            KeyEvent.getKeyText(KeyEvent.VK_A).toUpperCase())) {
        @Override
        public void startMoving(Movable entity) {
            entity.moveLeft();
        }

        @Override
        public boolean canMove(List<Wall> walls, List<Box> boxes, MainHero mainHero) {
            if (mainHero.getPositionX() - 1 < 0) {
                return false;
            }


            return true;
        }


    },
    DOWN(Arrays.asList(KeyEvent.getKeyText(KeyEvent.VK_DOWN).toUpperCase(),
            KeyEvent.getKeyText(KeyEvent.VK_S).toUpperCase())) {
        @Override
        public void startMoving(Movable entity) {
            entity.moveDown();
        }

        @Override
        public boolean canMove(List<Wall> walls, List<Box> boxes, MainHero mainHero) {
            if (mainHero.getPositionY() + Settings.OBJECT_SIZE + 1 > Settings.FIELD_HEIGHT) {
                return false;
            }
            return true;


        }


    },
    RIGHT(Arrays.asList(KeyEvent.getKeyText(KeyEvent.VK_RIGHT).toUpperCase(),
            KeyEvent.getKeyText(KeyEvent.VK_D).toUpperCase())) {
        @Override
        public void startMoving(Movable entity) {
            entity.moveRight();
        }

        @Override
        public boolean canMove(List<Wall> walls, List<Box> boxes, MainHero mainHero) {
            if (mainHero.getPositionX() + Settings.OBJECT_SIZE + 1 > Settings.FIELD_WIDTH) {
                return false;
            }


            return true;
        }
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

    public abstract void startMoving(Movable entity);

    public abstract boolean canMove(List<Wall> walls, List<Box> boxes, MainHero mainHero);

}
