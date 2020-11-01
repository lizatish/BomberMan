package ru.rsreu.tishkovets.events.data;

import ru.rsreu.tishkovets.events.data.object.PersonData;

public class MainHeroEventData implements EventData  {
    private final PersonData mainHeroData;

    public MainHeroEventData(PersonData mainHeroData) {
        this.mainHeroData = mainHeroData;
    }

    public PersonData getMainHeroData() {
        return mainHeroData;
    }

}
