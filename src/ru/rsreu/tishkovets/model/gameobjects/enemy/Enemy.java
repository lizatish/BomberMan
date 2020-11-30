package ru.rsreu.tishkovets.model.gameobjects.enemy;

import ru.rsreu.tishkovets.events.EventManager;
import ru.rsreu.tishkovets.events.EventType;
import ru.rsreu.tishkovets.events.MovableEventType;
import ru.rsreu.tishkovets.events.data.PersonEventData;
import ru.rsreu.tishkovets.events.data.object.PersonData;
import ru.rsreu.tishkovets.model.gameobjects.Person;

public class Enemy extends Person implements Runnable {

    private final ArtificialIntelligence aI;

    private boolean isMoving;
    private MovableEventType moveDirection;
    private boolean isAlive = true;

    public Enemy(double x, double y, double size, ArtificialIntelligence aI, EventManager eventManager) {
        super(x, y, size, eventManager);
        this.aI = aI;
        initAi();
    }

    private void initAi() {
        if (aI != null) {
            aI.setEnemy(this);
        }
    }

    @Override
    public void run() {
        while (this.isAlive) {
//            if (GameState.RUNNING.equals(Game.getGameState())) {
            calculateMove();
//            }
            try {
                Thread.sleep(8);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void update() {
        eventManager.notify(EventType.ENEMY_UPDATE, new PersonEventData(createEnemyData()));
    }

    public PersonData createEnemyData() {
        return new PersonData(getPositionX(), getPositionY(), getPrevPositionX(), getPrevPositionY(), getSize());
    }

    public void calculateMove() {
        moveDirection = aI.calculateDirection();
//        this.isMoving = true;

        if (moveDirection == MovableEventType.UP) {
            moveUp();
        } else if (moveDirection == MovableEventType.DOWN) {
            moveDown();
        } else if (moveDirection == MovableEventType.LEFT) {
            moveLeft();
        } else if (moveDirection == MovableEventType.RIGHT) {
            moveRight();
        }
    }
}
