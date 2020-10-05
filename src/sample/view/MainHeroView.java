package sample.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.Settings;
import sample.eventdata.EventData;
import sample.eventdata.EventListener;
import sample.eventdata.MainHeroData;

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
        MainHeroData mainHeroData = data.getMainHeroData();
        gc.clearRect(0, 0, Settings.FIELD_WIDTH, Settings.FIELD_HEIGHT);
        gc.drawImage(image, mainHeroData.getPositionX(), mainHeroData.getPositionY());
    }

    @Override
    public void update(EventData data) {
        render(data);
    }
}
