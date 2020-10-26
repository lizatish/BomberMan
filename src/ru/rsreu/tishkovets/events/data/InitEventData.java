package ru.rsreu.tishkovets.events.data;

import ru.rsreu.tishkovets.events.data.object.ObjectData;
import ru.rsreu.tishkovets.events.data.object.MainHeroData;

import java.util.List;

public class InitEventData implements EventData {
    private final List<ObjectData> wallsData;
    private final List<ObjectData> boxesData;
    private final MainHeroData mainHeroData;

    public InitEventData(MainHeroData mainHeroData, List<ObjectData> wallsData, List<ObjectData> boxesData) {
        this.wallsData = wallsData;
        this.boxesData = boxesData;
        this.mainHeroData = mainHeroData;
    }

    public List<ObjectData> getWallsData() {
        return wallsData;
    }

    public MainHeroData getMainHeroData() {
        return mainHeroData;
    }

    public List<ObjectData> getBoxesData() {
        return boxesData;
    }

}
