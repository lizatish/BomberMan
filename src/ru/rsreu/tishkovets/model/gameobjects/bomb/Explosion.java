package ru.rsreu.tishkovets.model.gameobjects.bomb;

import ru.rsreu.tishkovets.Settings;
import ru.rsreu.tishkovets.events.EventType;
import ru.rsreu.tishkovets.model.GameModel;

public class Explosion implements Runnable {
    private GameModel model;
    private boolean isAlive;
    private double positionX;
    private double positionY;
    private double size;

    public Explosion(double x, double y, double size, GameModel model) {
        this.positionX = x;
        this.positionY = y;
        this.model = model;
        this.isAlive = true;
    }

    private void explosionFinish() {
        isAlive = false;
        model.update(EventType.EXPLOSION_UPDATE);
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        isAlive = true;
        while (isAlive) {
           // while (isAlive && GameState.RUNNING.equals(Game.getGameState())) {

            if (System.currentTimeMillis() - startTime > Settings.EXPLOSION_TIME) {
                explosionFinish();
            }
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    @Override
    public String toString() {
        return "Bomb{" +
                "model=" + model +
                ", isAlive=" + isAlive +
                ", x=" + positionX +
                ", y=" + positionY +
                '}';
    }

    public double getSize() {
        return size;
    }

}
