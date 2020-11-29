package ru.rsreu.tishkovets.events.data;

import ru.rsreu.tishkovets.events.data.object.PersonData;

public class MainHeroEventData extends EventData {
    private final PersonData mainHeroData;

    public MainHeroEventData(PersonData mainHeroData) {
        super();
        this.mainHeroData = mainHeroData;
    }

    public PersonData getMainHeroData() {
        return mainHeroData;
    }

}
