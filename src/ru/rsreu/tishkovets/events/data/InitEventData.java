package ru.rsreu.tishkovets.events.data;

import ru.rsreu.tishkovets.events.data.object.BaseData;
import ru.rsreu.tishkovets.events.data.object.PersonData;

import java.util.List;

public class InitEventData extends EventData {
    private final List<BaseData> wallsData;
    private final List<PersonData> enemyesData;
    private final List<BaseData> boxesData;
    private final PersonData mainHeroData;

    public InitEventData(PersonData mainHeroData, List<BaseData> wallsData, List<PersonData> enemyes,
                         List<BaseData> boxesData) {
        super();
        this.wallsData = wallsData;
        this.enemyesData = enemyes;
        this.boxesData = boxesData;
        this.mainHeroData = mainHeroData;
    }

    public List<BaseData> getWallsData() {
        return wallsData;
    }

    public PersonData getMainHeroData() {
        return mainHeroData;
    }

    public List<BaseData> getBoxesData() {
        return boxesData;
    }

    public List<PersonData> getEnemyesData() {
        return enemyesData;
    }
}
