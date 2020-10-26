package ru.rsreu.tishkovets.events.data.object;


public class WallData {
    private final double positionX;
    private final double positionY;

    public WallData(double x, double y) {
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
