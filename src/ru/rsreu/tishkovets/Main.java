package ru.rsreu.tishkovets;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.rsreu.tishkovets.controller.GameController;
import ru.rsreu.tishkovets.events.EventManager;
import ru.rsreu.tishkovets.model.GameModel;
import ru.rsreu.tishkovets.view.GameView;

public class Main extends Application {

    public void start(Stage primaryStage) {
        EventManager eventManager = new EventManager();
        GameModel model = new GameModel(eventManager);
        GameController controller = new GameController(model);
        GameView gameView = new GameView(controller, eventManager);
        gameView.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
