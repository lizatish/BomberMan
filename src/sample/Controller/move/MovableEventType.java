package sample.Controller.move;


import sample.Settings;
import sample.Model.gameobjects.MainHero;
import sample.Model.gameobjects.Wall;

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
        public boolean canMove(List<Wall> walls, MainHero mainHero) {
            if (mainHero.getPositionY() - 1 < 0) {
                return false;
            }

            List<Integer> dir = new ArrayList<>(Arrays.asList(0, 1));
            for (Wall wall : walls) {
                double wallPositionX = wall.getPositionX();
                double wallPositionY = wall.getPositionY();
                double heroPositionX = mainHero.getPositionX();
                double heroPositionY = mainHero.getPositionY();

                if (wallPositionY > mainHero.getPositionY()) {
                    continue;
                }

                if (Math.abs(heroPositionX - wallPositionX) - dir.get(0) < Settings.FIELD_STEP - 2 * dir.get(1) &&
                        Math.abs(heroPositionY - wallPositionY) - dir.get(1) < Settings.FIELD_STEP - 2 * dir.get(0)) {
                    return false;
                }
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
        public boolean canMove(List<Wall> walls, MainHero mainHero) {
            if (mainHero.getPositionX() - 1 < 0) {
                return false;
            }

            List<Integer> dir = new ArrayList<>(Arrays.asList(1, 0));
            for (Wall wall : walls) {
                double wallPositionX = wall.getPositionX();
                double wallPositionY = wall.getPositionY();
                double heroPositionX = mainHero.getPositionX();
                double heroPositionY = mainHero.getPositionY();

                if (wallPositionX > mainHero.getPositionX()) {
                    continue;
                }

                if (Math.abs(heroPositionX - wallPositionX) - dir.get(0) < Settings.FIELD_STEP - 2 * dir.get(1) &&
                        Math.abs(heroPositionY - wallPositionY) - dir.get(1) < Settings.FIELD_STEP - 2 * dir.get(0)) {
                    return false;
                }
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
        public boolean canMove(List<Wall> walls, MainHero mainHero) {
            if (mainHero.getPositionY() + Settings.FIELD_STEP + 1 > Settings.FIELD_HEIGHT) {
                return false;
            }

            List<Integer> dir = new ArrayList<>(Arrays.asList(0, 1));
            for (Wall wall : walls) {
                double wallPositionX = wall.getPositionX();
                double wallPositionY = wall.getPositionY();
                double heroPositionX = mainHero.getPositionX();
                double heroPositionY = mainHero.getPositionY();

                if (wallPositionY < mainHero.getPositionY()) {
                    continue;
                }

                if (Math.abs(heroPositionX - wallPositionX) - dir.get(0) < Settings.FIELD_STEP - 2 * dir.get(1) &&
                        Math.abs(heroPositionY - wallPositionY) - dir.get(1) < Settings.FIELD_STEP - 2 * dir.get(0)) {
                    return false;
                }
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
        public boolean canMove(List<Wall> walls, MainHero mainHero) {
            if (mainHero.getPositionX() + Settings.FIELD_STEP + 1 > Settings.FIELD_WIDTH) {
                return false;
            }

            List<Integer> dir = new ArrayList<>(Arrays.asList(1, 0));
            for (Wall wall : walls) {
                double wallPositionX = wall.getPositionX();
                double wallPositionY = wall.getPositionY();
                double heroPositionX = mainHero.getPositionX();
                double heroPositionY = mainHero.getPositionY();

                if (wallPositionX < mainHero.getPositionX()) {
                    continue;
                }
                if (Math.abs(heroPositionX - wallPositionX) - dir.get(0) < Settings.FIELD_STEP - 2 * dir.get(1) &&
                        Math.abs(heroPositionY - wallPositionY) - dir.get(1) < Settings.FIELD_STEP - 2 * dir.get(0)) {
                    return false;
                }
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

    public abstract boolean canMove(List<Wall> walls, MainHero mainHero);

}
