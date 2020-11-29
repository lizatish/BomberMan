package ru.rsreu.tishkovets.view.object;

import javafx.scene.canvas.GraphicsContext;
import ru.rsreu.tishkovets.events.data.EventData;
import ru.rsreu.tishkovets.events.data.InitEventData;
import ru.rsreu.tishkovets.events.data.object.EnemyEventData;
import ru.rsreu.tishkovets.events.data.object.PersonData;

import java.util.List;


public class EnemyView extends BaseView {
    public EnemyView(GraphicsContext gcInit) {
        super(gcInit);
        imageFilename = "file:/home/liza/IdeaProjects/MVC_/src/ru/rsreu/tishkovets/view/resources/enemy.png";
    }

    @Override
    public void render(EventData data) {
        if (data instanceof EnemyEventData) {
            EnemyEventData renderData = (EnemyEventData) data;
            PersonData enemyData = renderData.getEnemyData();
            double enemyPositionX = enemyData.getPositionX();
            double enemyPositionY = enemyData.getPositionY();
            double enemyPrevPositionX = enemyData.getPrevPositionX();
            double enemyPrevPositionY = enemyData.getPrevPositionY();
            double enemySize = enemyData.getSize();

            gc.clearRect(enemyPrevPositionX, enemyPrevPositionY, enemySize, enemySize);
            gc.drawImage(image, enemyPositionX, enemyPositionY);


        } else if (data instanceof InitEventData) {
            InitEventData renderData = (InitEventData) data;
            List<PersonData> enemyesData = renderData.getEnemyesData();
            for (PersonData enemyData : enemyesData) {
                double enemyPositionX = enemyData.getPositionX();
                double enemyPositionY = enemyData.getPositionY();
                double enemySize = enemyData.getSize();

                if (!isImageSet) {
                    setImage(imageFilename, enemySize);
                    isImageSet = true;
                }
                gc.drawImage(image, enemyPositionX, enemyPositionY);
            }
        }
    }
}

