package ru.rsreu.tishkovets.model.gameobjects;

import ru.rsreu.tishkovets.events.EventManager;
import ru.rsreu.tishkovets.events.EventType;
import ru.rsreu.tishkovets.events.data.MainHeroEventData;
import ru.rsreu.tishkovets.events.data.object.PersonData;
import ru.rsreu.tishkovets.model.GameModel;

public class MainHero extends Person {

    public MainHero(double x, double y, double size, EventManager eventManager) {
        super(x, y, size, eventManager);
    }

    @Override
    public void update() {
        eventManager.notify(EventType.MAINHERO_UPDATE, new MainHeroEventData(createMainHeroData()));
    }

    public PersonData createMainHeroData() {
        return new PersonData(getPositionX(), getPositionY(), getPrevPositionX(), getPrevPositionY(), getSize());
    }

}
