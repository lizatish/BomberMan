package ru.rsreu.tishkovets.view.object;

import javafx.scene.canvas.GraphicsContext;
import ru.rsreu.tishkovets.Settings;
import ru.rsreu.tishkovets.events.data.EventData;
import ru.rsreu.tishkovets.events.data.InitEventData;
import ru.rsreu.tishkovets.events.data.PersonEventData;
import ru.rsreu.tishkovets.events.data.object.PersonData;

public class MainHeroView extends BaseView {
    public MainHeroView(GraphicsContext gcInit) {
        super(gcInit);
        imageFilename = "file:src/ru/rsreu/tishkovets/view/resources/mainHero.png";
    }

    @Override
    public void render(EventData data) {
        if (data instanceof PersonEventData) {
            PersonEventData renderData = (PersonEventData) data;

            PersonData mainHeroData = renderData.getPersonData();

            double mainHeroPositionX = mainHeroData.getPositionX();
            double mainHeroPositionY = mainHeroData.getPositionY();
            double mainHeroPrevPositionX = mainHeroData.getPrevPositionX();
            double mainHeroPrevPositionY = mainHeroData.getPrevPositionY();
            double mainHeroSize = mainHeroData.getSize();

            gc.clearRect(mainHeroPrevPositionX, mainHeroPrevPositionY, mainHeroSize, mainHeroSize);
            gc.drawImage(image, mainHeroPositionX, mainHeroPositionY);
        } else if (data instanceof InitEventData) {
            gc.clearRect(0, 0, Settings.FIELD_WIDTH, Settings.FIELD_HEIGHT);
            InitEventData renderData = (InitEventData) data;
            PersonData mainHeroData = renderData.getMainHeroData();

            double mainHeroPositionX = mainHeroData.getPositionX();
            double mainHeroPositionY = mainHeroData.getPositionY();
            double mainHeroSize = mainHeroData.getSize();

            if (!isImageSet) {
                setImage(imageFilename, mainHeroSize);
                isImageSet = true;
            }
            gc.drawImage(image, mainHeroPositionX, mainHeroPositionY);
        }
    }
}
