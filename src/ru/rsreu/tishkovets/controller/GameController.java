package ru.rsreu.tishkovets.controller;

import ru.rsreu.tishkovets.controller.move.MovableEventType;
import ru.rsreu.tishkovets.events.EventType;
import ru.rsreu.tishkovets.model.GameModel;

public class GameController {
    private final GameModel model;

    public GameController(GameModel model) {
        this.model = model;
    }

    public void move(MovableEventType eventType) {
        boolean canMove = model.canMove(eventType);
        if (canMove) {
            if (eventType == MovableEventType.UP) {
                model.getMainHero().moveUp();
            } else if (eventType == MovableEventType.DOWN) {
                model.getMainHero().moveDown();
            } else if (eventType == MovableEventType.LEFT) {
                model.getMainHero().moveLeft();
            } else if (eventType == MovableEventType.RIGHT) {
                model.getMainHero().moveRight();
            }
        }
        model.getMainHero().stop();
    }
    public void initModel() {
        model.update(EventType.INIT_UPDATE);
    }
}
