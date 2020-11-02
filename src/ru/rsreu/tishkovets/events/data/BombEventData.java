package ru.rsreu.tishkovets.events.data;

import ru.rsreu.tishkovets.events.data.object.StaticObjectData;
import java.util.List;


public class BombEventData implements EventData {
    private final List<StaticObjectData> bombsData;

    public BombEventData(List<StaticObjectData> mainHeroData) {
        this.bombsData = mainHeroData;
    }

    public List<StaticObjectData> getBombsData() {
        return bombsData;
    }

}