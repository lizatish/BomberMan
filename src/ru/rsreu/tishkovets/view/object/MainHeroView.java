package ru.rsreu.tishkovets.view.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import ru.rsreu.tishkovets.Settings;
import ru.rsreu.tishkovets.events.data.EventData;
import ru.rsreu.tishkovets.events.EventListener;
import ru.rsreu.tishkovets.events.data.MainHeroData;

public class MainHeroView implements EventListener {
    private Image image;
    private final GraphicsContext gc;
    private String imameFilename = "file:/home/liza/IdeaProjects/MVC_/src/ru/rsreu/tishkovets/view/resources/mainHero.png";
    private boolean isImageSet = false;

    public MainHeroView(GraphicsContext gcInit) {
        gc = gcInit;
    }

    public void setImage(String filename, double size) {
        image = new Image(filename, size, size, false, false);
    }

    public void render(EventData data) {
        MainHeroData mainHeroData = data.getMainHeroData();
        gc.clearRect(0, 0, Settings.FIELD_WIDTH, Settings.FIELD_HEIGHT);

        if (!isImageSet) {
            setImage(imameFilename, mainHeroData.getSize());
            isImageSet = true;
        }
        gc.drawImage(image, mainHeroData.getPositionX(), mainHeroData.getPositionY());
    }

    @Override
    public void update(EventData data) {
//        System.out.println("MainHero update!");
        render(data);
    }
}
