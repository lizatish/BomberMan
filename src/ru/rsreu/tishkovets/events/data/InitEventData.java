package ru.rsreu.tishkovets.events.data;

import ru.rsreu.tishkovets.events.data.object.BoxData;
import ru.rsreu.tishkovets.events.data.object.MainHeroData;
import ru.rsreu.tishkovets.events.data.object.WallData;

import java.util.List;

public class InitEventData implements EventData {
    private final List<WallData> wallsData;
    private final List<BoxData> boxesData;
    private final MainHeroData mainHeroData;

    public InitEventData(MainHeroData mainHeroData, List<WallData> wallsData, List<BoxData> boxesData) {
        this.wallsData = wallsData;
        this.boxesData = boxesData;
        this.mainHeroData = mainHeroData;
    }

    public List<WallData> getWallsData() {
        return wallsData;
    }

    public MainHeroData getMainHeroData() {
        return mainHeroData;
    }

    public List<BoxData> getBoxesData() {
        return boxesData;
    }

}
