package ru.rsreu.tishkovets.events.data;

import java.util.List;

public class EventData {
    private final MainHeroData mainHeroData;
    private final List<WallData> wallsData;
    private final List<BoxData> boxesData;


    public EventData(MainHeroData mainHeroData, List<WallData> wallsData, List<BoxData> boxesData) {
        this.mainHeroData = mainHeroData;
        this.wallsData = wallsData;
        this.boxesData = boxesData;
    }

    public MainHeroData getMainHeroData() {
        return mainHeroData;
    }

    public List<WallData> getWallsData() {
        return wallsData;
    }

    public List<BoxData> getBoxesData() {
        return boxesData;
    }

}
