package sample.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.EventData;
import sample.EventListener;
import sample.gameobjects.MainHero;

public class MainHeroView implements EventListener {
    private Image image;

    GraphicsContext gc;

    public MainHeroView(GraphicsContext gcInit) {
        gc = gcInit;
        setImage("file:/home/liza/IdeaProjects/MVC_/src/sample/resources/mainHero.png");
    }

    public void setImage(String filename) {
        image = new Image(filename, 60, 60, false, false);
    }

    public void render(EventData data) {
        gc.drawImage(image, data.getX(), data.getY());
    }

    @Override
    public void update(EventData data) {
        render(data);
    }
}
