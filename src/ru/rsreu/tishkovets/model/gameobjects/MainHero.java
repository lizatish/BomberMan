package ru.rsreu.tishkovets.model.gameobjects;

import ru.rsreu.tishkovets.events.EventManager;
import ru.rsreu.tishkovets.events.EventType;
import ru.rsreu.tishkovets.events.data.PersonEventData;
import ru.rsreu.tishkovets.events.data.object.PersonData;

public class MainHero extends Person {

    public MainHero(double x, double y, double size, EventManager eventManager) {
        super(x, y, size, eventManager);
    }

    @Override
    public void update() {
        eventManager.notify(EventType.MAINHERO_UPDATE, new PersonEventData(createMainHeroData()));
    }

    public PersonData createMainHeroData() {
        return new PersonData(getPositionX(), getPositionY(), getPrevPositionX(), getPrevPositionY(), getSize());
    }

}
