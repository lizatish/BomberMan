package sample.gameobjects;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Wall {

    private double positionX;
    private double positionY;
    private final double height = 60;
    private final double width = 60;
    private Image image;

    public Wall(double x, double y) {
        positionX = x;
        positionY = y;
        setImage("file:/home/liza/IdeaProjects/MVC_/src/sample/resources/wall.jpg");
    }

    public void setImage(String filename) {
        image = new Image(filename, 60, 60, false, false);
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, positionX, positionY);
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

}
