package ru.rsreu.tishkovets.view.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import ru.rsreu.tishkovets.Settings;
import ru.rsreu.tishkovets.events.data.EventData;
import ru.rsreu.tishkovets.events.EventListener;
import ru.rsreu.tishkovets.events.data.WallData;

import java.util.List;

public class WallsView implements EventListener {
    private Image image;
    GraphicsContext gc;

    public WallsView(GraphicsContext gcInit) {
        gc = gcInit;
        setImage("file:/home/liza/IdeaProjects/MVC_/src/ru/rsreu/tishkovets/view/resources/wall.jpg");
    }

    public void setImage(String filename) {
        image = new Image(filename, Settings.OBJECT_SIZE, Settings.OBJECT_SIZE, false, false);
    }

    public void render(EventData data) {
        List<WallData> walls = data.getWallsData();
        for (WallData wall : walls) {
            gc.drawImage(image, wall.getPositionX(), wall.getPositionY());
        }
    }

    @Override
    public void update(EventData data) {
//        System.out.println("Walls update");
        render(data);
    }
}
