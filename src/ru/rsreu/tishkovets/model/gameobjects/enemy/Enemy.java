package ru.rsreu.tishkovets.model.gameobjects.enemy;

import ru.rsreu.tishkovets.events.EventManager;
import ru.rsreu.tishkovets.events.EventType;
import ru.rsreu.tishkovets.events.MovableEventType;
import ru.rsreu.tishkovets.events.data.PersonEventData;
import ru.rsreu.tishkovets.events.data.object.PersonData;
import ru.rsreu.tishkovets.model.GameModel;
import ru.rsreu.tishkovets.model.GameState;
import ru.rsreu.tishkovets.model.gameobjects.Person;

public class Enemy extends Person implements Runnable {

    private final ArtificialIntelligence aI;
    private MovableEventType moveDirection;
    private GameModel model;
    private boolean isAlive = true;

    public Enemy(double x, double y, double size, ArtificialIntelligence aI, GameModel model, EventManager eventManager) {
        super(x, y, size, eventManager);
        this.model = model;
        this.aI = aI;
        initAi();
    }

    @Override
    public void run() {
        while (this.isAlive && GameState.RUNNING.equals(GameModel.getGameState())) {
            try {
                calculateMove();
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void update() {
        eventManager.notify(EventType.ENEMY_UPDATE, new PersonEventData(createEnemyData(false)));
    }

    public PersonData createEnemyData(boolean isDelete) {
        return new PersonData(getPositionX(), getPositionY(), getPrevPositionX(),
                getPrevPositionY(), getSize(), isDelete);
    }

    private void calculateMove() {

        moveDirection = aI.calculateDirection();

        if (moveDirection == MovableEventType.UP) {
            moveUp();
        } else if (moveDirection == MovableEventType.DOWN) {
            moveDown();
        } else if (moveDirection == MovableEventType.LEFT) {
            moveLeft();
        } else if (moveDirection == MovableEventType.RIGHT) {
            moveRight();
        }

        if (model.checkEnemyOnDeath(this)) {
            model.removeEnemy(this);
            this.isAlive = false;
        }
        if (model.checkMainHeroOnDeath()) {
            GameModel.setGameState(GameState.FINISHED);
            System.out.println("GAME OVER");
        }
    }

    private void initAi() {
        if (aI != null) {
            aI.setEnemy(this);
        }
    }
}
