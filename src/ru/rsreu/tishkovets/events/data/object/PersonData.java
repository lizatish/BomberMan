package ru.rsreu.tishkovets.events.data.object;

public class PersonData extends BaseData {
    private final double prevPositionX;
    private final double prevPositionY;
    private boolean isDelete;

    public PersonData(double x, double y, double px, double py, double size) {
        this(x, y, px, py, size, false);
    }

    public PersonData(double x, double y, double px, double py, double size, boolean isDelete) {
        super(x, y, size);
        prevPositionX = px;
        prevPositionY = py;
        this.isDelete = isDelete;
    }

    public double getPrevPositionX() {
        return prevPositionX;
    }

    public double getPrevPositionY() {
        return prevPositionY;
    }

    @Override
    public boolean isDelete() {
        return isDelete;
    }
}
