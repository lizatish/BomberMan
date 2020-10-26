package ru.rsreu.tishkovets.view.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import ru.rsreu.tishkovets.events.data.EventData;
import ru.rsreu.tishkovets.events.data.InitEventData;
import ru.rsreu.tishkovets.events.data.ModelUpdateEventData;
import ru.rsreu.tishkovets.events.EventListener;
import ru.rsreu.tishkovets.events.data.object.MainHeroData;

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
        if (data instanceof ModelUpdateEventData) {
            ModelUpdateEventData renderData = (ModelUpdateEventData) data;
            MainHeroData mainHeroData = renderData.getMainHeroData();

            double mainHeroPositionX = mainHeroData.getPositionX();
            double mainHeroPositionY = mainHeroData.getPositionY();
            double mainHeroPrevPositionX = mainHeroData.getPrevPositionX();
            double mainHeroPrevPositionY = mainHeroData.getPrevPositionY();
            double mainHeroSize = mainHeroData.getSize();

            gc.clearRect(mainHeroPrevPositionX, mainHeroPrevPositionY, mainHeroSize, mainHeroSize);

            if (!isImageSet) {
                setImage(imameFilename, mainHeroSize);
                isImageSet = true;
            }
            gc.drawImage(image, mainHeroPositionX, mainHeroPositionY);
        } else if (data instanceof InitEventData) {
            InitEventData renderData = (InitEventData) data;
            MainHeroData mainHeroData = renderData.getMainHeroData();

            double mainHeroPositionX = mainHeroData.getPositionX();
            double mainHeroPositionY = mainHeroData.getPositionY();
            double mainHeroSize = mainHeroData.getSize();

            gc.clearRect(mainHeroPositionX, mainHeroPositionY, mainHeroSize, mainHeroSize);

            if (!isImageSet) {
                setImage(imameFilename, mainHeroSize);
                isImageSet = true;
            }
            gc.drawImage(image, mainHeroPositionX, mainHeroPositionY);
        }

    }

    @Override
    public void update(EventData data) {
//        System.out.println("MainHero update!");
        render(data);
    }
}
