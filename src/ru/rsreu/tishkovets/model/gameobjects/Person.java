package ru.rsreu.tishkovets.model.gameobjects;


import ru.rsreu.tishkovets.events.EventType;
import ru.rsreu.tishkovets.model.GameModel;
import ru.rsreu.tishkovets.model.move.Movable;

public abstract class Person implements Movable {
    private double positionX;
    private double positionY;
    private double prevPositionX;
    private double prevPositionY;
    private final double size;
    private double speed = 1;
    private final GameModel model;

    Person(double x, double y, double size, GameModel model) {
        this.model = model;
        this.size = size;
        positionX = x;
        positionY = y;
    }

    @Override
    public void moveUp() {
        setVelocity(0, -speed);
    }

    @Override
    public void moveDown() {
        setVelocity(0, speed);
    }

    @Override
    public void moveLeft() {
        setVelocity(-speed, 0);
    }

    @Override
    public void moveRight() {
        setVelocity(speed, 0);
    }

    @Override
    public void stop() {
        setVelocity(0, 0);
    }

    private void setVelocity(double x, double y) {
        prevPositionX = positionX;
        prevPositionY = positionY;
        positionX += x;
        positionY += y;
        model.update(EventType.MODEL_UPDATE);
    }

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