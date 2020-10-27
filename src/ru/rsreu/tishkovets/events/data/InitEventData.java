package ru.rsreu.tishkovets.events.data;

import ru.rsreu.tishkovets.events.data.object.StaticObjectData;
import ru.rsreu.tishkovets.events.data.object.PersonData;

import java.util.List;

public class InitEventData implements EventData {
    private final List<StaticObjectData> wallsData;
    private final List<PersonData> enemyesData;
    private final List<StaticObjectData> boxesData;
    private final PersonData mainHeroData;

    public InitEventData(PersonData mainHeroData, List<StaticObjectData> wallsData, List<PersonData> enemyes, List<StaticObjectData> boxesData) {
        this.wallsData = wallsData;
        this.enemyesData = enemyes;
        this.boxesData = boxesData;
        this.mainHeroData = mainHeroData;
    }

    public List<StaticObjectData> getWallsData() {
        return wallsData;
    }

    public PersonData getMainHeroData() {
        return mainHeroData;
    }

    public List<StaticObjectData> getBoxesData() {
        return boxesData;
    }

    public List<PersonData> getEnemyesData() {
        return enemyesData;
    }
}
