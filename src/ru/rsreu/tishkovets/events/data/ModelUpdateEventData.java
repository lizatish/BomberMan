package ru.rsreu.tishkovets.events.data;

import ru.rsreu.tishkovets.events.data.object.ObjectData;
import ru.rsreu.tishkovets.events.data.object.MainHeroData;

import java.util.List;

public class ModelUpdateEventData implements EventData {
    private final MainHeroData mainHeroData;
    private final List<ObjectData> bombsData;

    public ModelUpdateEventData(MainHeroData mainHeroData, List<ObjectData> bombsData) {
        this.mainHeroData = mainHeroData;
        this.bombsData = bombsData;
    }

    public MainHeroData getMainHeroData() {
        return mainHeroData;
    }

    public List<ObjectData> getBombsData() {
        return bombsData;
    }
}
