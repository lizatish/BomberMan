package ru.rsreu.tishkovets.model.gameobjects;

import ru.rsreu.tishkovets.Settings;
import ru.rsreu.tishkovets.events.EventManager;
import ru.rsreu.tishkovets.events.EventType;
import ru.rsreu.tishkovets.events.data.object.BaseData;
import ru.rsreu.tishkovets.events.data.BaseEventData;

public class Bomb implements Runnable {
    private final double positionX;
    private final double positionY;
    private final double size;

    private final EventManager eventManager;
    private boolean isAlive;

    public Bomb(double x, double y, double size, EventManager eventManager) {
        this.positionX = x;
        this.positionY = y;
        this.size = size;
        this.eventManager = eventManager;
    }

    public void explode() {

    }

    private void boom() {
        System.out.println("Booom");
        isAlive = false;
        eventManager.notify(EventType.BOMB_UPDATE, new BaseEventData(createBombData()));
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        isAlive = true;
//        while (isAlive && GameState.RUNNING.equals(model.getGameState())) {
        while (isAlive) {
            if (System.currentTimeMillis() - startTime > Settings.BOMB_ALIVE_TIME) {
                boom();
            }
//            eventManager.notify(EventType.BOMB_UPDATE, new BombEventData(createBombData()));
        }
    }

    public BaseData createBombData() {
        return new BaseData(getPositionX(), getPositionY(), getSize());
    }

    public double getPositionY() {
        return positionY;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getSize() {
        return size;
    }
}
