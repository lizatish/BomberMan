package ru.rsreu.tishkovets.events.data;

import ru.rsreu.tishkovets.model.GameState;

public class ScoreEventData extends EventData {
    private final int score;
    private final GameState gameState;


    public ScoreEventData(int score, GameState gameState) {
        this.score = score;
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getScore() {
        return score;
    }
}
