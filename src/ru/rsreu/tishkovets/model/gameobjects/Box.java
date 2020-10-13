package ru.rsreu.tishkovets.model.gameobjects;

public class Box {
    private final double positionX;
    private final double positionY;

    public Box(double x, double y) {
        positionX = x;
        positionY = y;
    }

    public double getPositionX() {
        return positionX;
    }
    public double getPositionY() {
        return positionY;
    }
}
