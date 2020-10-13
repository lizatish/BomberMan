package ru.rsreu.tishkovets.model;

import javafx.util.Pair;
import ru.rsreu.tishkovets.Settings;
import ru.rsreu.tishkovets.events.data.BoxData;
import ru.rsreu.tishkovets.events.data.EventData;
import ru.rsreu.tishkovets.events.EventManager;
import ru.rsreu.tishkovets.events.data.MainHeroData;
import ru.rsreu.tishkovets.events.data.WallData;
import ru.rsreu.tishkovets.model.gameobjects.Box;
import ru.rsreu.tishkovets.model.gameobjects.MainHero;
import ru.rsreu.tishkovets.model.gameobjects.Wall;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private final EventManager eventManager;

    private final MainHero mainHero = new MainHero(0, 0, Settings.OBJECT_SIZE, this);
    private final List<Wall> walls = new ArrayList<>();
    private final List<Box> boxes = new ArrayList<>();

    public GameModel(EventManager eventManager) {
        this.eventManager = eventManager;
        generateWallsAndBoxes();

    }

    public void generateWallsAndBoxes() {
        double horizontalStep = Settings.FIELD_WIDTH / (2 * Settings.WALLS_NUMBER_IN_ROW + 1);

        if (horizontalStep < Settings.OBJECT_SIZE) {
            horizontalStep = Settings.OBJECT_SIZE;
        }
        for (double i = horizontalStep; i < Settings.FIELD_WIDTH; i += 2 * horizontalStep) {
            for (double j = Settings.OBJECT_SIZE; j < Settings.FIELD_HEIGHT; j += 2 * Settings.OBJECT_SIZE) {
                Wall temp = new Wall(i, j);
                walls.add(temp);
            }
        }

        int currentBoxesNumber = 0;
        while (currentBoxesNumber < Settings.BOXES_NUMBER) {
            int x = getRandomNumber(0, Settings.FIELD_WIDTH - 1);
            int y = getRandomNumber(0, Settings.FIELD_HEIGHT - 1);
            x -= x % Settings.OBJECT_SIZE;
            y -= y % Settings.OBJECT_SIZE;
            if (x < 2 * Settings.OBJECT_SIZE && 2 * y < Settings.OBJECT_SIZE ||
                    x > Settings.FIELD_WIDTH - 2 * Settings.OBJECT_SIZE
                            && y > Settings.FIELD_HEIGHT - 2 * Settings.OBJECT_SIZE) {
                continue;
            }

            if (!walls.contains(new Wall(x, y))) {
                boxes.add(new Box(x, y));
                currentBoxesNumber += 1;
            }
        }
    }

    public void update() {
        MainHeroData mainHeroData = new MainHeroData(mainHero.getPositionX(), mainHero.getPositionY(), mainHero.getSize());
        List<WallData> wallsData = new ArrayList<>();
        for (Wall wall : walls) {
            WallData temp = new WallData(wall.getPositionX(), wall.getPositionY());
            wallsData.add(temp);
        }
        List<BoxData> boxesData = new ArrayList<>();
        for (Box box : boxes) {
            BoxData temp = new BoxData(box.getPositionX(), box.getPositionY());
            boxesData.add(temp);
        }
        EventData data = new EventData(mainHeroData, wallsData, boxesData);
        eventManager.notifyAllListeners(data);
    }

    public int getRandomNumber(double min, double max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public MainHero getMainHero() {
        return mainHero;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Box> getBoxes() {
        return boxes;
    }


    public boolean canMove() {
        if (mainHero.getPositionX() + Settings.OBJECT_SIZE + 1 > Settings.FIELD_WIDTH) {
            return false;
        }

        Rectangle mainHeroRect = new Rectangle((int) mainHero.getPositionX(), (int) mainHero.getPositionY(),
                (int) Settings.OBJECT_SIZE, (int) Settings.OBJECT_SIZE);
        for (Wall wall : walls) {
            Rectangle wallRect = new Rectangle((int) wall.getPositionX(), (int) wall.getPositionY(),
                    (int) Settings.OBJECT_SIZE, (int) Settings.OBJECT_SIZE);

            System.out.println(mainHeroRect.intersection(wallRect));
            if (mainHeroRect.intersects(wallRect)) {
                return false;
            }
        }
        for (Box box : boxes) {
            Rectangle wallRect = new Rectangle((int) box.getPositionX(), (int) box.getPositionY(),
                    (int) Settings.OBJECT_SIZE, (int) Settings.OBJECT_SIZE);

            System.out.println(mainHeroRect.intersection(wallRect));
            if (mainHeroRect.intersects(wallRect)) {
                return false;
            }
        }
        return true;
    }

}


