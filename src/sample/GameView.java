package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.eventdata.EventData;
import sample.eventdata.EventListener;
import sample.view.MainHeroView;
import sample.view.WallView;

import java.util.ArrayList;


public class GameView implements EventListener {
    GraphicsContext gc;

    private final GameController controller;
    MainHeroView mainHV;
    ArrayList<WallView> walls = new ArrayList<>();

    Stage primaryStage;

    public GameView(GameController controller) {
        this.controller = controller;
    }

//    public void start() {
//        controller.start();
//    }

    @Override
    public void update(EventData data) {
        System.out.println("Im update!");

        mainHV.render(data);
        for (WallView wall : walls) {
            wall.render(data);
        }
    }

    public void start() {
        primaryStage = new Stage();
        primaryStage.setTitle("BomberMan");
        Group root = new Group();
        Scene theScene = new Scene(root, Settings.FIELD_WIDTH, Settings.FIELD_HEIGHT, Color.LIMEGREEN);

        primaryStage.setScene(theScene);

        Canvas canvas = new Canvas(Settings.FIELD_WIDTH, Settings.FIELD_HEIGHT);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();

        mainHV = new MainHeroView(gc);
        for (int i = 60; i < Settings.FIELD_HEIGHT; i += 2 * Settings.FIELD_STEP) {
            for (int j = 60; j < Settings.FIELD_WIDTH; j += 2 * Settings.FIELD_STEP) {
                WallView temp = new WallView(gc);
                walls.add(temp);
            }
        }

        ArrayList<String> input = new ArrayList<>();
        theScene.setOnKeyPressed(
                e -> {
                    String code = e.getCode().toString();
                    if (!input.contains(code))
                        input.add(code);
                });


        theScene.setOnKeyPressed(
                e -> {
                    String code = e.getCode().toString();
                    if (!input.contains(code))
                        input.add(code);
                });

        theScene.setOnKeyReleased(
                e -> {
                    String code = e.getCode().toString();
                    input.remove(code);
                });


        controller.checkUserChanges(input);
        primaryStage.show();
    }

}
