package ru.rsreu.tishkovets.model.gameobjects;

import java.util.Objects;

public class Wall {

    private final double positionX;
    private final double positionY;

    public Wall(double x, double y) {
        positionX = x;
        positionY = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wall wall = (Wall) o;
        return Double.compare(wall.positionX, positionX) == 0 &&
                Double.compare(wall.positionY, positionY) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionX, positionY);
    }

    public double getPositionX() {
        return positionX;
    }
    public double getPositionY() {
        return positionY;
    }

}
