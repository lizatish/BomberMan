package ru.rsreu.tishkovets.view.object;

import javafx.scene.canvas.GraphicsContext;
import ru.rsreu.tishkovets.events.data.EventData;
import ru.rsreu.tishkovets.events.data.InitEventData;
import ru.rsreu.tishkovets.events.data.ModelUpdateEventData;
import ru.rsreu.tishkovets.events.data.object.MainHeroData;

public class MainHeroView extends BaseView {
    public MainHeroView(GraphicsContext gcInit) {
        super(gcInit);
        imageFilename = "file:/home/liza/IdeaProjects/MVC_/src/ru/rsreu/tishkovets/view/resources/mainHero.png";
    }

    @Override
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
                setImage(imageFilename, mainHeroSize);
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
                setImage(imageFilename, mainHeroSize);
                isImageSet = true;
            }
            gc.drawImage(image, mainHeroPositionX, mainHeroPositionY);
        }
    }
}
