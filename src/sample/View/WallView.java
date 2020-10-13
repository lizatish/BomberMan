package sample.View;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.eventdata.EventData;
import sample.eventdata.EventListener;
import sample.eventdata.WallData;

import java.util.ArrayList;

public class WallView implements EventListener {
    private Image image;
    GraphicsContext gc;

    public WallView(GraphicsContext gcInit) {
        gc = gcInit;
        setImage("file:/home/liza/IdeaProjects/MVC_/src/sample/View/resources/wall.jpg");
    }

    public void setImage(String filename) {
        image = new Image(filename, 60, 60, false, false);
    }

    public void render(EventData data) {
        ArrayList<WallData> walls = data.getWallsData();

        for (WallData wall : walls) {
            gc.drawImage(image, wall.getPositionX(), wall.getPositionY());
        }
    }

    @Override
    public void update(EventData data) {
//        System.out.println("1");
        render(data);
    }
}
