package sample.Model.gameobjects;

public class Wall {

    private final double positionX;
    private final double positionY;

    public Wall(double x, double y) {
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
