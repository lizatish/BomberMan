package sample.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.EventData;
import sample.EventListener;
import sample.gameobjects.Wall;

import java.util.ArrayList;

public class WallView implements EventListener {
    private Image image;
    GraphicsContext gc;
    public WallView(GraphicsContext gcInit) {
        gc = gcInit;
        setImage("file:/home/liza/IdeaProjects/MVC_/src/sample/resources/wall.jpg");
    }

    public void setImage(String filename) {
        image = new Image(filename, 60, 60, false, false);
    }

    public void render(EventData data) {
        ArrayList<Wall> walls = data.getWalls();

        for(Wall wall: walls){
            gc.drawImage(image, wall.getPositionX(), wall.getPositionY());
        }
    }

    @Override
    public void update(EventData data) {
        render(data);
    }
}
