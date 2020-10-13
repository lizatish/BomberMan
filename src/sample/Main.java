package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Controller.GameController;
import sample.Controller.move.MovableEventType;
import sample.Model.Bombers;
import sample.View.MainHeroView;
import sample.View.WallView;
import sample.eventdata.EventManager;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private GraphicsContext gc;
    List<String> keyboardInput;


    @Override
    public void start(Stage primaryStage) {
        initializeGameSceneAndKeyboard(primaryStage);

        EventManager eventManager = new EventManager();
        Bombers model = new Bombers(eventManager);
        GameController controller = new GameController(model);
        MainHeroView mainHeroView = new MainHeroView(gc);
        WallView temp = new WallView(gc);

        eventManager.subscribe(mainHeroView);
        eventManager.subscribe(temp);

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                MovableEventType eventType = MovableEventType.getMovableOperationByKeyName(keyboardInput);
                if (eventType != null) {
                    controller.move(eventType);
                }
            }
        }.start();
    }

    private void initializeGameSceneAndKeyboard(Stage primaryStage) {
        primaryStage.setTitle("BomberMan");
        Group root = new Group();
        Scene theScene = new Scene(root, Settings.FIELD_WIDTH, Settings.FIELD_HEIGHT, Color.LIMEGREEN);
        primaryStage.setScene(theScene);
        Canvas canvas = new Canvas(Settings.FIELD_WIDTH, Settings.FIELD_HEIGHT);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        initializeKeyboard(theScene);
        primaryStage.show();
    }

    private void initializeKeyboard(Scene theScene) {
        keyboardInput = new ArrayList<>();
        theScene.setOnKeyPressed(
                e -> {
                    String code = e.getCode().toString();
                    if (!keyboardInput.contains(code))
                        keyboardInput.add(code);
                });

        theScene.setOnKeyReleased(
                e -> {
                    String code = e.getCode().toString();
                    keyboardInput.remove(code);
                });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
