package ru.rsreu.tishkovets.controller;

import ru.rsreu.tishkovets.events.GameEventType;
import ru.rsreu.tishkovets.events.MovableEventType;
import ru.rsreu.tishkovets.model.GameModel;
import ru.rsreu.tishkovets.model.GameState;
import ru.rsreu.tishkovets.model.Serializator;

public class GameController {
    private GameModel model;
    private final Serializator serializator = new Serializator();

    public GameController(GameModel model) {
        this.model = model;
    }

    public void move(MovableEventType eventType) {
        boolean canMove = model.canMainHeroMove(eventType);
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

        if (model.checkMainHeroOnDeath()) {
            GameModel.setGameState(GameState.END);
            model.finishGame();
        }
    }

    public void startAction(GameEventType gameEventType) {
        if (gameEventType == GameEventType.LOAD_GAME) {
            model = serializator.loadState();
            if (model != null) {
                gameEventType.startAction(model);
            }
        } else if (gameEventType == GameEventType.START) {
            if (GameModel.getGameState().equals(GameState.NEW)) {
                gameEventType.startAction(model);
            }
        } else {
            gameEventType.startAction(model);
        }
    }
}