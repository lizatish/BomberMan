package ru.rsreu.tishkovets.events.data;

import ru.rsreu.tishkovets.events.data.object.PersonData;


public class PersonEventData extends EventData {
    private final PersonData personData;

    public PersonEventData(PersonData personData) {
        this.personData = personData;
    }

    public PersonData getPersonData() {
        return personData;
    }

}