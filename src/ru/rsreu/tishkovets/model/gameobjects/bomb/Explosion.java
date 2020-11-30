package ru.rsreu.tishkovets.model.gameobjects.bomb;

import ru.rsreu.tishkovets.Settings;
import ru.rsreu.tishkovets.events.EventManager;
import ru.rsreu.tishkovets.events.EventType;
import ru.rsreu.tishkovets.events.data.BaseEventData;
import ru.rsreu.tishkovets.events.data.object.BaseData;
import ru.rsreu.tishkovets.model.GameModel;

public class Explosion implements Runnable {
    private GameModel model;
    private boolean isAlive;
    private double positionX;
    private double positionY;
    private double size;
    private EventManager eventManager;

    public Explosion(double x, double y, double size, EventManager eventManager, GameModel model) {
        this.positionX = x;
        this.positionY = y;
        this.size = size;
        this.model = model;
        this.isAlive = true;
        this.eventManager = eventManager;
    }

    private void explosionFinish() {
        isAlive = false;
//        eventManager.notify(EventType.EXPLOSION_UPDATE, new BaseEventData(createExplosionData()));
        model.removeExplosion(this);
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
    public BaseData createExplosionData() {
        return new BaseData(getPositionX(), getPositionY(), getSize());
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
