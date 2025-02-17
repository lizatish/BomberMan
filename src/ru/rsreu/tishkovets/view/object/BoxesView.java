package ru.rsreu.tishkovets.view.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import ru.rsreu.tishkovets.Settings;
import ru.rsreu.tishkovets.events.EventListener;
import ru.rsreu.tishkovets.events.data.BaseEventData;
import ru.rsreu.tishkovets.events.data.EventData;
import ru.rsreu.tishkovets.events.data.InitEventData;
import ru.rsreu.tishkovets.events.data.object.BaseData;

import java.util.List;

public class BoxesView implements EventListener {
    private Image image;
    GraphicsContext gc;

    public BoxesView(GraphicsContext gcInit) {
        gc = gcInit;
        setImage("file:src/ru/rsreu/tishkovets/view/resources/box.jpeg");
    }

    public void setImage(String filename) {
        image = new Image(filename, Settings.OBJECT_SIZE, Settings.OBJECT_SIZE, false, false);
    }

    public void render(EventData data) {
        if (data instanceof InitEventData) {
            InitEventData renderData = (InitEventData) data;
            List<BaseData> boxes = renderData.getBoxesData();
            for (BaseData box : boxes) {
                gc.drawImage(image, box.getPositionX(), box.getPositionY());
            }
        } else if (data instanceof BaseEventData) {
            BaseEventData renderData = (BaseEventData) data;
            BaseData box = renderData.getBaseData();
            gc.clearRect(box.getPositionX(), box.getPositionY(), box.getSize(), box.getSize());
        }
    }

    @Override
    public void update(EventData data) {
        render(data);
    }
}
