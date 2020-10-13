package ru.rsreu.tishkovets.controller;

import ru.rsreu.tishkovets.controller.move.MovableEventType;
import ru.rsreu.tishkovets.model.GameModel;

public class GameController {
    private final GameModel model;

    public GameController(GameModel model) {
        this.model = model;
    }

    public void move(MovableEventType eventType) {
        boolean canMove = eventType.canMove(model.getWalls(), model.getBoxes(), model.getMainHero());
        if (canMove) {
            eventType.startMoving(model.getMainHero());
        }
        model.getMainHero().stop();
    }
}
