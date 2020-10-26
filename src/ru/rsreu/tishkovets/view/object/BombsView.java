package ru.rsreu.tishkovets.view.object;

import javafx.scene.canvas.GraphicsContext;
import ru.rsreu.tishkovets.events.data.EventData;
import ru.rsreu.tishkovets.events.data.InitEventData;
import ru.rsreu.tishkovets.events.data.ModelUpdateEventData;
import ru.rsreu.tishkovets.events.data.object.BombData;
import ru.rsreu.tishkovets.events.data.object.MainHeroData;
import ru.rsreu.tishkovets.events.data.object.WallData;

import java.util.List;


public class BombsView extends BaseView {
    public BombsView(GraphicsContext gcInit) {
        super(gcInit);
        imageFilename = "file:/home/liza/IdeaProjects/MVC_/src/ru/rsreu/tishkovets/view/resources/bomb.png";
    }

    @Override
    public void render(EventData data) {
        ModelUpdateEventData renderData = (ModelUpdateEventData) data;


        List<BombData> bombs = renderData.getBombsData();
        for (BombData bomb : bombs) {
            if (!isImageSet) {
                setImage(imageFilename, bomb.getSize());
                isImageSet = true;
            }
            gc.drawImage(image, bomb.getPositionX(), bomb.getPositionY());
        }
    }
}