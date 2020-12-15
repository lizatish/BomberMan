package ru.rsreu.tishkovets.model.gameobjects.enemy;


import ru.rsreu.tishkovets.events.MovableEventType;
import ru.rsreu.tishkovets.model.GameModel;

import java.io.Serializable;


public class ArtificialIntelligence implements Serializable {

    private GameModel model;
    private Enemy enemy;

    public ArtificialIntelligence(GameModel model) {
        this.model = model;
    }

    public MovableEventType calculateDirection() {
        MovableEventType moveDirectionType = null;
        double dx = enemy.getPositionX() - model.getMainHero().getPositionX();
        double dy = enemy.getPositionY() - model.getMainHero().getPositionY();
        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx < 0) {
                moveDirectionType = getMovableEventType(dy, MovableEventType.RIGHT, MovableEventType.DOWN, MovableEventType.UP);
            } else {
                moveDirectionType = getMovableEventType(dy, MovableEventType.LEFT, MovableEventType.DOWN, MovableEventType.UP);
            }
        } else {
            if (dy < 0) {
                moveDirectionType = getMovableEventType(dx, MovableEventType.DOWN, MovableEventType.RIGHT, MovableEventType.LEFT);
            } else {
                if (dy > 0) {
                    moveDirectionType = getMovableEventType(dx, MovableEventType.UP, MovableEventType.RIGHT, MovableEventType.LEFT);
                }
            }
        }

        model.removeBoxesInCollision(enemy);
        return moveDirectionType;
    }

    private MovableEventType getMovableEventType(double direction, MovableEventType original, MovableEventType newDirectionA, MovableEventType newDirectionB) {
        MovableEventType moveDirectionType = original;
        if (!model.canEnemyMove(moveDirectionType, enemy)) {
            if (direction < 0) {
                moveDirectionType = newDirectionA;
                if (!model.canEnemyMove(moveDirectionType, enemy)) {
                    moveDirectionType = newDirectionB;
                }
            } else {
                moveDirectionType = newDirectionB;
                if (!model.canEnemyMove(moveDirectionType, enemy)) {
                    moveDirectionType = newDirectionA;
                }
            }
        }
        return moveDirectionType;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }
}
