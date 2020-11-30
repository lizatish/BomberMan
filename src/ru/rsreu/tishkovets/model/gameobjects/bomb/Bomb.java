package ru.rsreu.tishkovets.model.gameobjects.bomb;

import ru.rsreu.tishkovets.Settings;
import ru.rsreu.tishkovets.events.EventManager;
import ru.rsreu.tishkovets.events.EventType;
import ru.rsreu.tishkovets.events.data.object.BaseData;
import ru.rsreu.tishkovets.events.data.BaseEventData;
import ru.rsreu.tishkovets.model.GameModel;
import ru.rsreu.tishkovets.model.gameobjects.BaseObject;

public class Bomb extends BaseObject implements Runnable {

    private final EventManager eventManager;
    private boolean isAlive;
    private final GameModel model;

    public Bomb(double x, double y, double size, EventManager eventManager, GameModel model) {
        super(x, y, size);
        this.eventManager = eventManager;
        this.model = model;
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
                if (System.currentTimeMillis() - startTime > Settings.BOMB_ALIVE_TIME) {
                    boom();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void boom() {
        System.out.println("Booom");
        isAlive = false;
        model.explodeBomb(this);
    }

    private BaseData createBombData() {
        return new BaseData(getPositionX(), getPositionY(), getSize());
    }

}
