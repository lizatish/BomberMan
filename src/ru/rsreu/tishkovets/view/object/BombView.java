package ru.rsreu.tishkovets.view.object;

import javafx.scene.canvas.GraphicsContext;
import ru.rsreu.tishkovets.events.data.BaseEventData;
import ru.rsreu.tishkovets.events.data.EventData;
import ru.rsreu.tishkovets.events.data.object.BaseData;


public class BombView extends BaseView {
    public BombView(GraphicsContext gcInit) {
        super(gcInit);
        imageFilename = "file:/home/liza/IdeaProjects/MVC_/src/ru/rsreu/tishkovets/view/resources/bomb.png";
    }

    @Override
    public void render(EventData data) {
        BaseEventData renderData = (BaseEventData) data;
        BaseData bomb = renderData.getBaseData();
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