package ru.rsreu.tishkovets.events.data;

public class BoxData {
    private final double positionX;
    private final double positionY;

    public BoxData(double x, double y) {
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
