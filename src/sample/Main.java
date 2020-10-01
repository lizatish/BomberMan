package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import sample.gameobjects.MainHero;
import sample.gameobjects.Wall;
import sample.view.WallView;

import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Application {

    private final double FIELD_WIDTH = 1260;
    private final double FIELD_HEIGHT = 780;
    //private final double FIELD_WIDTH = 300;
    //private final double FIELD_HEIGHT = 300;
    private final double FIELD_STEP = 60;

    @Override
    public void start(Stage primaryStage) throws Exception {
        EventManager eventManager = new EventManager();
        Model model = new Model(eventManager);
        Controller controller = new Controller(model);
//        WallView wallView = new WallView();
        View view = new View(controller);
        eventManager.subscribe(view);
        view.start();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
