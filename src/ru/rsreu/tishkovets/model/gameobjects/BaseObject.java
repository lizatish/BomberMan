package ru.rsreu.tishkovets.model.gameobjects;


import java.io.Serializable;

public abstract class BaseObject implements Serializable {
    protected double positionX;
    protected double positionY;
    protected double prevPositionX;
    protected double prevPositionY;
    protected final double size;

    public BaseObject(double x, double y, double size) {
        this.size = size;
        positionX = x;
        positionY = y;
        prevPositionX = x;
        prevPositionY = y;
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

    public double getPrevPositionX() {
        return prevPositionX;
    }

    public double getPrevPositionY() {
        return prevPositionY;
    }

}
