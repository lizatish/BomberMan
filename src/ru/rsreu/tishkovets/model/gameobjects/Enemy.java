package ru.rsreu.tishkovets.model.gameobjects;

import ru.rsreu.tishkovets.events.EventType;
import ru.rsreu.tishkovets.model.GameModel;

public class Enemy extends Person {
    public Enemy(double x, double y, double size, GameModel model) {
        super(x, y, size, model);
    }

    @Override
    public void update() {
        model.update(EventType.ENEMY_UPDATE);
    }
}
