package ru.rsreu.tishkovets.events.data.object;


public class BombData {
    private final double positionX;
    private final double positionY;
    private final double size;

    public BombData(double x, double y, double size) {
        positionX = x;
        positionY = y;
        this.size = size;

    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public double getSize() {
        return size;
    }
}
