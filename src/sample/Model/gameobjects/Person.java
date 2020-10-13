package sample.Model.gameobjects;


import sample.Model.Bombers;
import sample.Controller.move.Movable;

public abstract class Person implements Movable {
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double speed = 50;
    Bombers model;

    Person(double x, double y, Bombers model) {
        this.model = model;
        positionX = x;
        positionY = y;
        velocityX = 0;
        velocityY = 0;
    }

    public void update(double time) {
        positionX += velocityX * time;
        positionY += velocityY * time;
    }

    @Override
    public void moveUp() {
        setVelocity(0, -speed);
    }

    @Override
    public void moveDown() {
        setVelocity(0, speed);
    }

    @Override
    public void moveLeft() {
        setVelocity(-speed, 0);
    }

    @Override
    public void moveRight() {
        setVelocity(speed, 0);
    }

    @Override
    public void stop() {
        setVelocity(0, 0);
    }

    private void setVelocity(double x, double y) {
        velocityX = x;
        velocityY = y;
        positionX += velocityX * 0.01;
        positionY += velocityY * 0.01;
        model.update();
    }

    public String toString() {
        return " Position: [" + positionX + "," + positionY + "]"
                + " Velocity: [" + velocityX + "," + velocityY + "]";
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }
}