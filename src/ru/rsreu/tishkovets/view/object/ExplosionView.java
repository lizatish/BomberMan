package ru.rsreu.tishkovets.view.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import ru.rsreu.tishkovets.Settings;
import ru.rsreu.tishkovets.events.data.BaseEventData;
import ru.rsreu.tishkovets.events.data.EventData;
import ru.rsreu.tishkovets.events.data.ExplosionsEventData;
import ru.rsreu.tishkovets.events.data.object.BaseData;

import java.util.List;


public class ExplosionView extends BaseView {
    public ExplosionView(GraphicsContext gcInit) {
        super(gcInit);
        imageFilename = "file:/home/liza/IdeaProjects/MVC_/src/ru/rsreu/tishkovets/view/resources/explosion.png";
        setImage(imageFilename);
    }

    public void setImage(String filename) {
        image = new Image(filename, Settings.OBJECT_SIZE, Settings.OBJECT_SIZE, false, false);
    }

    @Override
    public void render(EventData data) {
        ExplosionsEventData renderData = (ExplosionsEventData) data;
        List<BaseData> explosions = renderData.getExplosionsData();
        for (BaseData explosion : explosions) {
            gc.clearRect(explosion.getPositionX(), explosion.getPositionY(),
                    explosion.getSize(), explosion.getSize());
            gc.drawImage(image, explosion.getPositionX(), explosion.getPositionY());
        }
    }
}