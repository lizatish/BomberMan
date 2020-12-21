package ru.rsreu.tishkovets.model;

import javax.swing.*;
import java.io.*;

public class Serializator {

    public synchronized void saveState(GameAction gameAction) {

        GameModel gameModel = (GameModel) gameAction;
        GameModel.setGameState(GameState.PAUSED);
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;

        try {

            JFrame parentFrame = new JFrame();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");

            int userSelection = fileChooser.showSaveDialog(parentFrame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

                fos = new FileOutputStream(fileToSave);
                oos = new ObjectOutputStream(fos);
                synchronized (gameModel) {
                    oos.writeObject(gameModel);
                }
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
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Specify a file to open");
            int status = chooser.showOpenDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                if (file == null) {
                    return null;
                }
                String fileName = chooser.getSelectedFile().getAbsolutePath();
                fis = new FileInputStream(fileName);
                ois = new ObjectInputStream(fis);
                synchronized (this) {
                    gameModel = (GameModel) ois.readObject();
                }
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
