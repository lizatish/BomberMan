package ru.rsreu.tishkovets.model.gameobjects.bomb;

import ru.rsreu.tishkovets.Settings;
import ru.rsreu.tishkovets.model.GameModel;
import ru.rsreu.tishkovets.model.GameState;
import ru.rsreu.tishkovets.model.gameobjects.BaseObject;

import java.io.Serializable;

public class Explosion extends BaseObject implements Runnable {
    private final GameModel model;
    private boolean isAlive;

    public Explosion(double x, double y, double size, GameModel model) {
        super(x, y, size);
        this.model = model;
        this.isAlive = true;
    }

    private void explosionFinish() {
        isAlive = false;
        model.removeExplosion(this);
        model.removeBoxesInCollision(this);
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        isAlive = true;
        while (isAlive && GameState.RUNNING.equals(GameModel.getGameState())) {
            if (System.currentTimeMillis() - startTime > Settings.EXPLOSION_TIME) {
                explosionFinish();
            }
        }
    }
}
