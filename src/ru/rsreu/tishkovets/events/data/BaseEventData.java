package ru.rsreu.tishkovets.events.data;

import ru.rsreu.tishkovets.events.data.object.BaseData;


public class BaseEventData extends EventData {
    private final BaseData baseData;

    public BaseEventData(BaseData baseData) {
        this.baseData = baseData;
    }

    public BaseData getBaseData() {
        return baseData;
    }

}