package sample.Model;

import sample.Settings;
import sample.eventdata.EventData;
import sample.eventdata.EventManager;
import sample.eventdata.MainHeroData;
import sample.eventdata.WallData;
import sample.Model.gameobjects.MainHero;
import sample.Model.gameobjects.Wall;

import java.util.ArrayList;

public class Bombers {
    private final EventManager eventManager;

    private final MainHero mainHero = new MainHero(0, 0, this);
    private final ArrayList<Wall> walls = new ArrayList<>();

    public Bombers(EventManager eventManager) {
        this.eventManager = eventManager;

        for (int i = 60; i < Settings.FIELD_HEIGHT; i += 2 * Settings.FIELD_STEP) {
            for (int j = 60; j < Settings.FIELD_WIDTH; j += 2 * Settings.FIELD_STEP) {
                Wall temp = new Wall(j, i);
                walls.add(temp);
            }
        }
    }


    public void update() {
        MainHeroData mainHeroData = new MainHeroData(mainHero.getPositionX(), mainHero.getPositionY());
        ArrayList<WallData> wallsData = new ArrayList<>();
        for (Wall wall: walls){
            WallData temp = new WallData(wall.getPositionX(), wall.getPositionY());
            wallsData.add(temp);
        }
        EventData data = new EventData(mainHeroData, wallsData);
        eventManager.notifyAllListeners(data);
    }

    public MainHero getMainHero() {
        return mainHero;
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }
}


