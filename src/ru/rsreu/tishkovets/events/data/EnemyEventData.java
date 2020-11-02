package ru.rsreu.tishkovets.events.data;

import ru.rsreu.tishkovets.events.data.object.PersonData;

import java.util.List;


public class EnemyEventData implements EventData {
    private final PersonData enemyData;

    public EnemyEventData(PersonData enemyData) {
        this.enemyData = enemyData;
    }

    public PersonData getEnemyData() {
        return enemyData;
    }

}