package ru.rsreu.tishkovets.model;

import java.io.*;

public class Serializator {
    public synchronized void saveState(GameAction gameAction) {

        GameModel gameModel = (GameModel) gameAction;
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream("SaveState.save");
            oos = new ObjectOutputStream(fos);
            synchronized (this) {
                oos.writeObject(gameModel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.flush();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized GameModel loadState() {
        ObjectInputStream ois = null;
        FileInputStream fis;
        GameModel gameModel = null;

        try {
            fis = new FileInputStream("SaveState.save");
            ois = new ObjectInputStream(fis);
            synchronized (this) {
                gameModel = (GameModel) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return gameModel;
    }
}
