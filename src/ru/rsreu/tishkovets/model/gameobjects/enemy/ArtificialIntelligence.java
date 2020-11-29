package ru.rsreu.tishkovets.model.gameobjects.enemy;


import ru.rsreu.tishkovets.events.EventManager;
import ru.rsreu.tishkovets.events.EventType;
import ru.rsreu.tishkovets.events.MovableEventType;
import ru.rsreu.tishkovets.model.GameModel;
import ru.rsreu.tishkovets.model.gameobjects.Box;

import java.awt.*;
import java.util.List;
import java.util.Random;


public class ArtificialIntelligence {

    private GameModel model;
    private Enemy enemy;

    List<MovableEventType> VALUES = List.of(MovableEventType.values());
    Random RANDOM = new Random();

    public ArtificialIntelligence(GameModel model) {
        this.model = model;
    }

    public MovableEventType calculateDirection() {
        MovableEventType moveDirectionType = null;
        double dx = enemy.getPositionX() - model.getMainHero().getPositionX();
        double dy = enemy.getPositionY() - model.getMainHero().getPositionY();
        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx < 0) {
                moveDirectionType = MovableEventType.RIGHT;
                if (!model.canEnemyMove(moveDirectionType, enemy)) {
                    if (dy < 0) {
                        moveDirectionType = MovableEventType.DOWN;
                        if (!model.canEnemyMove(moveDirectionType, enemy)) {
                            moveDirectionType = MovableEventType.UP;
                        }
                    } else {
                        moveDirectionType = MovableEventType.UP;
                        if (!model.canEnemyMove(moveDirectionType, enemy)) {
                            moveDirectionType = MovableEventType.DOWN;
                        }
                    }
                }
            } else {
                moveDirectionType = MovableEventType.LEFT;
                if (!model.canEnemyMove(moveDirectionType, enemy)) {
                    if (dy < 0) {
                        moveDirectionType = MovableEventType.DOWN;
                        if (!model.canEnemyMove(moveDirectionType, enemy)) {
                            moveDirectionType = MovableEventType.UP;
                        }
                    } else {
                        moveDirectionType = MovableEventType.UP;
                        if (!model.canEnemyMove(moveDirectionType, enemy)) {
                            moveDirectionType = MovableEventType.DOWN;
                        }
                    }
                }
            }
        } else {
            if (dy < 0) {
                moveDirectionType = MovableEventType.DOWN;
                if (!model.canEnemyMove(moveDirectionType, enemy)) {
                    if (dx < 0) {
                        moveDirectionType = MovableEventType.RIGHT;
                        if (!model.canEnemyMove(moveDirectionType, enemy)) {
                            moveDirectionType = MovableEventType.LEFT;
                        }
                    } else {
                        moveDirectionType = MovableEventType.LEFT;
                        if (!model.canEnemyMove(moveDirectionType, enemy)) {
                            moveDirectionType = MovableEventType.RIGHT;
                        }
                    }
                }
            } else {
                if (dy > 0) {
                    moveDirectionType = MovableEventType.UP;
                    if (!model.canEnemyMove(moveDirectionType, enemy)) {
                        if (dx < 0) {
                            moveDirectionType = MovableEventType.RIGHT;
                            if (!model.canEnemyMove(moveDirectionType, enemy)) {
                                moveDirectionType = MovableEventType.LEFT;
                            }
                        } else {
                            moveDirectionType = MovableEventType.LEFT;
                            if (!model.canEnemyMove(moveDirectionType, enemy)) {
                                moveDirectionType = MovableEventType.RIGHT;
                            }
                        }
                    }
                }
            }
        }

        model.deleteWallIfEnemyCollision(enemy);
        return moveDirectionType;
    }


    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }
}
