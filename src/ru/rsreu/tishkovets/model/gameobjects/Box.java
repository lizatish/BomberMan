package ru.rsreu.tishkovets.model.gameobjects;

import ru.rsreu.tishkovets.events.data.object.BaseData;

public class Box {
    private final double positionX;
    private final double positionY;
    private final double size;


    public Box(double x, double y, double size) {
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

    public BaseData createBoxData() {
        return new BaseData(getPositionX(), getPositionY(), getSize());
    }
}
