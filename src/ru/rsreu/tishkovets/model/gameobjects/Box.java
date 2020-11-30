package ru.rsreu.tishkovets.model.gameobjects;

import ru.rsreu.tishkovets.events.data.object.BaseData;

public class Box extends BaseObject {

    public Box(double x, double y, double size) {
        super(x, y, size);

    }

    public BaseData createBoxData() {
        return new BaseData(getPositionX(), getPositionY(), getSize());
    }
}
