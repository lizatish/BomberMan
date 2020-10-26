package ru.rsreu.tishkovets.view.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import ru.rsreu.tishkovets.Settings;
import ru.rsreu.tishkovets.events.EventListener;
import ru.rsreu.tishkovets.events.data.EventData;
import ru.rsreu.tishkovets.events.data.InitEventData;
import ru.rsreu.tishkovets.events.data.object.ObjectData;

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
        InitEventData renderData = (InitEventData)data;
        List<ObjectData> boxes = renderData.getBoxesData();
        for (ObjectData box : boxes) {
            gc.drawImage(image, box.getPositionX(), box.getPositionY());
        }
    }

    @Override
    public void update(EventData data) {
//        System.out.println("Walls update");
        render(data);
    }
}
