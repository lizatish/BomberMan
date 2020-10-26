package ru.rsreu.tishkovets.events.data;

import ru.rsreu.tishkovets.events.data.object.BombData;
import ru.rsreu.tishkovets.events.data.object.MainHeroData;

import java.util.List;

public class ModelUpdateEventData implements EventData {
    private final MainHeroData mainHeroData;
    private final List<BombData> bombsData;

    public ModelUpdateEventData(MainHeroData mainHeroData, List<BombData> bombsData) {
        this.mainHeroData = mainHeroData;
        this.bombsData = bombsData;
    }

    public MainHeroData getMainHeroData() {
        return mainHeroData;
    }

    public List<BombData> getBombsData() {
        return bombsData;
    }
}
