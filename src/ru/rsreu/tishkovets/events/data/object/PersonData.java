package ru.rsreu.tishkovets.events.data.object;

public class PersonData extends BaseData {
    private final double prevPositionX;
    private final double prevPositionY;

    public PersonData(double x, double y, double px, double py, double size) {
        super(x, y, size);
        prevPositionX = px;
        prevPositionY = py;
    }

    public double getPrevPositionX() {
        return prevPositionX;
    }

    public double getPrevPositionY() {
        return prevPositionY;
    }
}
