package sample;

import sample.gameobjects.MainHero;
import sample.gameobjects.Wall;

import java.util.ArrayList;

public class Model {
    private final double FIELD_WIDTH = 1260;
    private final double FIELD_HEIGHT = 780;
    private final double FIELD_STEP = 60;
    private EventManager eventManager;

    MainHero mainHero = new MainHero(0, 0, this);
    ArrayList<Wall> walls = new ArrayList<>();

    public Model(EventManager eventManager) {
        this.eventManager = eventManager;
        mainHero.setImage("file:/home/liza/IdeaProjects/MVC_/src/sample/resources/mainHero.png");

        for (int i = 60; i < FIELD_HEIGHT; i += 2 * FIELD_STEP) {
            for (int j = 60; j < FIELD_WIDTH; j += 2 * FIELD_STEP) {
                Wall temp = new Wall(j, i);
                walls.add(temp);
            }
        }
    }


    public void update() {
        EventData data = new EventData(mainHero.getPositionX(), mainHero.getPositionY(), walls);
        eventManager.notifyAllListeners(data);
    }

}


