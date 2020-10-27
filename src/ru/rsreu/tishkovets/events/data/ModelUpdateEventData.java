package ru.rsreu.tishkovets.events.data;

import ru.rsreu.tishkovets.events.data.object.StaticObjectData;
import ru.rsreu.tishkovets.events.data.object.PersonData;

import java.util.List;

public class ModelUpdateEventData implements EventData {
    private final PersonData mainHeroData;
    private final List<StaticObjectData> bombsData;
    private final List<PersonData> enemyesData;


    public ModelUpdateEventData(PersonData mainHeroData, List<StaticObjectData> bombsData, List<PersonData> enemyesData) {
        this.mainHeroData = mainHeroData;
        this.bombsData = bombsData;
        this.enemyesData = enemyesData;
    }

    public PersonData getMainHeroData() {
        return mainHeroData;
    }

    public List<StaticObjectData> getBombsData() {
        return bombsData;
    }

    public List<PersonData> getEnemyesData() {
        return enemyesData;
    }
}
