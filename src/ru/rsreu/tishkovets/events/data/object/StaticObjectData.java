package ru.rsreu.tishkovets.events.data.object;

public class StaticObjectData {
    private final double positionX;
    private final double positionY;
    private final double size;

    public StaticObjectData(double x, double y, double size) {
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
