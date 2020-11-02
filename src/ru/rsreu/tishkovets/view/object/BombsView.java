package ru.rsreu.tishkovets.view.object;

import javafx.scene.canvas.GraphicsContext;
import ru.rsreu.tishkovets.events.data.BombEventData;
import ru.rsreu.tishkovets.events.data.EventData;
import ru.rsreu.tishkovets.events.data.object.StaticObjectData;

import java.util.List;


public class BombsView extends BaseView {
    public BombsView(GraphicsContext gcInit) {
        super(gcInit);
        imageFilename = "file:/home/liza/IdeaProjects/MVC_/src/ru/rsreu/tishkovets/view/resources/bomb.png";
    }

    @Override
    public void render(EventData data) {
        BombEventData renderData = (BombEventData) data;
        StaticObjectData bomb = renderData.getBombData();
        if (!isImageSet) {
            setImage(imageFilename, bomb.getSize());
            isImageSet = true;
        }
        gc.drawImage(image, bomb.getPositionX(), bomb.getPositionY());

    }

    @Override
    public void update(EventData data) {
//        System.out.println("Walls update");
        render(data);
    }
}