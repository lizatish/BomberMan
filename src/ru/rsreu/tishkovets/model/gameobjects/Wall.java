package ru.rsreu.tishkovets.model.gameobjects;

import java.util.Objects;

public class Wall extends BaseObject {
    public Wall(double x, double y, double size) {
        super(x, y, size);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wall wall = (Wall) o;
        return Double.compare(wall.positionX, positionX) == 0 &&
                Double.compare(wall.positionY, positionY) == 0 &&
                Double.compare(wall.size, size) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionX, positionY, size);
    }

}
