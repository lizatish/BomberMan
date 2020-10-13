package ru.rsreu.tishkovets.model.gameobjects;


import ru.rsreu.tishkovets.model.GameModel;
import ru.rsreu.tishkovets.controller.move.Movable;

public abstract class Person implements Movable {
    private double positionX;
    private double positionY;
    private final double size;
    private double speed = 1;
    private GameModel model;

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
        positionX += x;
        positionY += y;
        model.update();
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
}