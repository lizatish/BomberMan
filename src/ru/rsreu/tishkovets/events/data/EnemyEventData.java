package ru.rsreu.tishkovets.events.data;

import ru.rsreu.tishkovets.events.data.object.PersonData;

import java.util.List;


public class EnemyEventData implements EventData {
    private final List<PersonData> enemyesData;

    public EnemyEventData(List<PersonData> mainHeroData) {
        this.enemyesData = mainHeroData;
    }

    public List<PersonData> getEnemyesData() {
        return enemyesData;
    }

}