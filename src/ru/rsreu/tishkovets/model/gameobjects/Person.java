package ru.rsreu.tishkovets.model.gameobjects;


import ru.rsreu.tishkovets.events.EventManager;
import ru.rsreu.tishkovets.model.GameModel;
import ru.rsreu.tishkovets.model.GameState;

public abstract class Person extends BaseObject {
    protected double speed = 1;
    protected EventManager eventManager;

    public Person(double x, double y, double size, EventManager eventManager) {
        super(x, y, size);
        this.eventManager = eventManager;
    }

    public void moveUp() {
        setVelocity(0, -speed);
    }

    public void moveDown() {
        setVelocity(0, speed);
    }

    public void moveLeft() {
        setVelocity(-speed, 0);
    }

    public void moveRight() {
        setVelocity(speed, 0);
    }

    public void stop() {
        setVelocity(0, 0);
    }

    private void setVelocity(double x, double y) {
        if (GameState.RUNNING.equals(GameModel.getGameState())) {
            prevPositionX = positionX;
            prevPositionY = positionY;
            positionX += x;
            positionY += y;
            update();
        }
    }

    public abstract void update();

    public double getSize() {
        return size;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public double getSpeed() {
        return speed;
    }

    public double getPrevPositionX() {
        return prevPositionX;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getPrevPositionY() {
        return prevPositionY;
    }
}