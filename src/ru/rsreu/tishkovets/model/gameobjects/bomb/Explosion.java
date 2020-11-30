package ru.rsreu.tishkovets.model.gameobjects.bomb;

import ru.rsreu.tishkovets.Settings;
import ru.rsreu.tishkovets.model.GameModel;
import ru.rsreu.tishkovets.model.gameobjects.BaseObject;

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
        model.deleteBoxesInCollision(this);
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        isAlive = true;
        while (isAlive) {
            // while (isAlive && GameState.RUNNING.equals(Game.getGameState())) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (System.currentTimeMillis() - startTime > Settings.EXPLOSION_TIME) {
                explosionFinish();
            }
        }
    }
}
