package ru.rsreu.tishkovets.model;

public interface GameAction {
    
    void placeBomb();

    void pause(boolean isPaused);

    boolean isPaused();

    void start();
}
