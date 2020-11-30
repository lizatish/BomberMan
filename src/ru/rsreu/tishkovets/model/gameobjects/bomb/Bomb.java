package ru.rsreu.tishkovets.model.gameobjects.bomb;

import ru.rsreu.tishkovets.Settings;
import ru.rsreu.tishkovets.events.EventManager;
import ru.rsreu.tishkovets.events.EventType;
import ru.rsreu.tishkovets.events.data.object.BaseData;
import ru.rsreu.tishkovets.events.data.BaseEventData;
import ru.rsreu.tishkovets.model.GameModel;

public class Bomb implements Runnable {
    private final double positionX;
    private final double positionY;
    private final double size;

    private final EventManager eventManager;
    private boolean isAlive;
    private final GameModel model;

    public Bomb(double x, double y, double size, EventManager eventManager, GameModel model) {
        this.positionX = x;
        this.positionY = y;
        this.size = size;
        this.eventManager = eventManager;
        this.model = model;
    }

    public void explode() {
        model.explodeBomb(this);
    }

    private void boom() {
        System.out.println("Booom");
        isAlive = false;
//        eventManager.notify(EventType.EXPLODE_BOMB, new BaseEventData(createBombData()));
        explode();
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        isAlive = true;

//        while (isAlive && GameState.RUNNING.equals(model.getGameState())) {
        while (isAlive) {
            try {
                Thread.sleep(50);
                eventManager.notify(EventType.PLACE_BOMB, new BaseEventData(createBombData()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (System.currentTimeMillis() - startTime > Settings.BOMB_ALIVE_TIME) {
                boom();
            }
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

    public boolean isAlive() {
        return isAlive;
    }
}
