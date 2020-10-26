package ru.rsreu.tishkovets.view.object;

import javafx.scene.canvas.GraphicsContext;
import ru.rsreu.tishkovets.events.data.EventData;


public class ExplosionView extends BaseView {
    public ExplosionView(GraphicsContext gcInit) {
        super(gcInit);
        imageFilename = "file:/home/liza/IdeaProjects/MVC_/src/ru/rsreu/tishkovets/view/resources/explosion.png";
    }

    @Override
    public void render(EventData data) {
    }
}