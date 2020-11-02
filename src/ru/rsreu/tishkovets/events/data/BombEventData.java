package ru.rsreu.tishkovets.events.data;

import ru.rsreu.tishkovets.events.data.object.StaticObjectData;

import java.util.List;


public class BombEventData implements EventData {
    private final StaticObjectData bombData;

    public BombEventData(StaticObjectData bombData) {
        this.bombData = bombData;
    }

    public StaticObjectData getBombData() {
        return bombData;
    }

}