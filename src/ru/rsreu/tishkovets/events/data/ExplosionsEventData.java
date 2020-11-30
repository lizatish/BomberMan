package ru.rsreu.tishkovets.events.data;

import ru.rsreu.tishkovets.events.data.object.BaseData;

import java.util.List;

public class ExplosionsEventData extends EventData {
    private final List<BaseData> explosionsData;

    public ExplosionsEventData(List<BaseData> explosionsData) {
        this.explosionsData = explosionsData;

    }

    public List<BaseData> getExplosionsData() {
        return explosionsData;
    }
}
