package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.eventdata.EventManager;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage){
        EventManager eventManager = new EventManager();
        Bombers model = new Bombers(eventManager);
        GameController controller = new GameController(model);
        GameView view = new GameView(controller);
        eventManager.subscribe(view);
        view.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
