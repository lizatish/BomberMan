package ru.rsreu.tishkovets.events.data.object;

public class MainHeroData {
    private final double positionX;
    private final double positionY;
    private final double prevPositionX;
    private final double prevPositionY;
    private final double size;

    public MainHeroData(double x, double y, double px, double py, double size) {
        positionX = x;
        positionY = y;
        prevPositionX = px;
        prevPositionY = py;
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

    public double getPrevPositionX() {
        return prevPositionX;
    }

    public double getPrevPositionY() {
        return prevPositionY;
    }
}
