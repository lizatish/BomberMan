package ru.rsreu.tishkovets.events.data.object;


public class BaseData {
    private final double positionX;
    private final double positionY;
    private final double size;
    private boolean isDelete;

    public BaseData(double x, double y, double size){
        this(x, y, size, false);
    }

    public BaseData(double x, double y, double size, boolean isDelete) {
        positionX = x;
        positionY = y;
        this.size = size;
        this.isDelete = isDelete;
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

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public boolean isDelete() {
        return isDelete;
    }
}