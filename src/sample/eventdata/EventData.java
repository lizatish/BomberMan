package sample.eventdata;

import java.util.ArrayList;

public class EventData {
    MainHeroData mainHeroData;
    ArrayList<WallData> wallsData;


    public EventData(MainHeroData mainHeroData, ArrayList<WallData> wallsData) {
        this.mainHeroData = mainHeroData;
        this.wallsData = wallsData;
    }

    public MainHeroData getMainHeroData() {
        return mainHeroData;
    }
    public ArrayList<WallData>  getWallsData() {
        return wallsData;
    }
}
