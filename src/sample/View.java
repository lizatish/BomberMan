package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import sample.gameobjects.MainHero;
import sample.gameobjects.Wall;
import sample.view.MainHeroView;
import sample.view.WallView;

import java.util.ArrayList;


public class View implements EventListener {

    private final double FIELD_WIDTH = 1260;
    private final double FIELD_HEIGHT = 780;
    private final double FIELD_STEP = 60;
    GraphicsContext gc;

    private Controller controller;
    MainHeroView mainHV;
    ArrayList<WallView> walls = new ArrayList<>();

    Stage primaryStage;

    public View(Controller controller) {
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
        Scene theScene = new Scene(root);

        primaryStage.setScene(theScene);


        Canvas canvas = new Canvas(FIELD_WIDTH, FIELD_HEIGHT);
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();
        mainHV = new MainHeroView(gc);

        for (int i = 60; i < FIELD_HEIGHT; i += 2 * FIELD_STEP) {
            for (int j = 60; j < FIELD_WIDTH; j += 2 * FIELD_STEP) {
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

        theScene.setOnKeyReleased(
                e -> {
                    String code = e.getCode().toString();
                    input.remove(code);
                });


        controller.checkUserChanges(input);

        primaryStage.show();

    }
}
