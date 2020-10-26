package ru.rsreu.tishkovets.model.gameobjects;

import ru.rsreu.tishkovets.model.GameModel;

public class Bomb {
    private final double positionX;
    private final double positionY;
    private final double size;

    private final GameModel model;

    public Bomb(double x, double y, double size, GameModel model) {
        this.positionX = x;
        this.positionY = y;
        this.size = size;
        this.model = model;
    }

    public void explode() {

    }

    public double getPositionY() {
        return positionY;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getSize() {
        return size;
    }
}
