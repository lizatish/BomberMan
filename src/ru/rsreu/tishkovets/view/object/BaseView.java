package ru.rsreu.tishkovets.view.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import ru.rsreu.tishkovets.events.EventListener;
import ru.rsreu.tishkovets.events.data.EventData;

public abstract class BaseView implements EventListener {
    protected Image image;
    protected final GraphicsContext gc;
    protected String imageFilename;
    protected boolean isImageSet = false;

    public BaseView(GraphicsContext gcInit) {
        gc = gcInit;
    }

    public void setImage(String filename, double size) {
        image = new Image(filename, size, size, false, false);
    }

     public void render(EventData data) {
    }

    @Override
    public void update(EventData data) {
        render(data);
    }
}
