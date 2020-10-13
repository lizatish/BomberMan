package ru.rsreu.tishkovets.view.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import ru.rsreu.tishkovets.Settings;
import ru.rsreu.tishkovets.events.EventListener;
import ru.rsreu.tishkovets.events.data.BoxData;
import ru.rsreu.tishkovets.events.data.EventData;
import ru.rsreu.tishkovets.events.data.WallData;
import ru.rsreu.tishkovets.model.gameobjects.Box;

import java.util.List;

public class BoxesView implements EventListener {
    private Image image;
    GraphicsContext gc;

    public BoxesView(GraphicsContext gcInit) {
        gc = gcInit;
        setImage("file:/home/liza/IdeaProjects/MVC_/src/ru/rsreu/tishkovets/view/resources/box.jpeg");
    }

    public void setImage(String filename) {
        image = new Image(filename, Settings.OBJECT_SIZE, Settings.OBJECT_SIZE, false, false);
    }

    public void render(EventData data) {
        List<BoxData> boxes = data.getBoxesData();
        for (BoxData box : boxes) {
            gc.drawImage(image, box.getPositionX(), box.getPositionY());
        }
    }

    @Override
    public void update(EventData data) {
//        System.out.println("Walls update");
        render(data);
    }
}
