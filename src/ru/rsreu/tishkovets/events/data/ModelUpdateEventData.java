package ru.rsreu.tishkovets.events.data;

import ru.rsreu.tishkovets.events.data.object.BoxData;
import ru.rsreu.tishkovets.events.data.object.MainHeroData;
import ru.rsreu.tishkovets.events.data.object.WallData;

import java.util.List;

public class ModelUpdateEventData implements EventData{
    private final MainHeroData mainHeroData;

    public ModelUpdateEventData(MainHeroData mainHeroData) {
        this.mainHeroData = mainHeroData;
    }

    public MainHeroData getMainHeroData() {
        return mainHeroData;
    }

}
