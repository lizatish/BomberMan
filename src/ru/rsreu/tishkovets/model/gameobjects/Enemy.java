package ru.rsreu.tishkovets.model.gameobjects;

import ru.rsreu.tishkovets.events.EventManager;
import ru.rsreu.tishkovets.events.EventType;
import ru.rsreu.tishkovets.events.data.EnemyEventData;
import ru.rsreu.tishkovets.events.data.object.PersonData;

public class Enemy extends Person {

    public Enemy(double x, double y, double size, EventManager eventManager) {
        super(x, y, size, eventManager);
    }

    @Override
    public void update() {
        eventManager.notify(EventType.ENEMY_UPDATE, new EnemyEventData(createEnemyData()));
    }

    public PersonData createEnemyData() {
        return new PersonData(getPositionX(), getPositionY(), getPrevPositionX(), getPrevPositionY(), getSize());
    }
}
