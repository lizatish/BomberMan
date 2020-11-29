package ru.rsreu.tishkovets.events.data.object;

import ru.rsreu.tishkovets.events.data.EventData;


public class BombEventData extends EventData {
    private final BaseData bombData;

    public BombEventData(BaseData bombData) {
        this.bombData = bombData;
    }

    public BaseData getBombData() {
        return bombData;
    }

}