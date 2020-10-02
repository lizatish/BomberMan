package sample;

import sample.gameobjects.Wall;

import java.util.ArrayList;

public class EventData {
    double x;
    double y;

    ArrayList<Wall> walls;

    public EventData(double x, double y, ArrayList<Wall> walls) {
        this.x = x;
        this.y = y;
        this.walls = walls;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

}
