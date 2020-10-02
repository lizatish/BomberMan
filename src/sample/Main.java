package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage){
        EventManager eventManager = new EventManager();
        Model model = new Model(eventManager);
        Controller controller = new Controller(model);
        View view = new View(controller);
        eventManager.subscribe(view);
        view.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
