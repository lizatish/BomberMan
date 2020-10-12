package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Controller.GameController;
import sample.Model.Bombers;
import sample.View.MainHeroView;
import sample.View.WallView;
import sample.eventdata.EventManager;

import java.util.ArrayList;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        EventManager eventManager = new EventManager();
        Bombers model = new Bombers(eventManager);
        GameController controller = new GameController(model);
        primaryStage.setTitle("BomberMan");
        Group root = new Group();
        Scene theScene = new Scene(root, Settings.FIELD_WIDTH, Settings.FIELD_HEIGHT, Color.LIMEGREEN);
        primaryStage.setScene(theScene);
        Canvas canvas = new Canvas(Settings.FIELD_WIDTH, Settings.FIELD_HEIGHT);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        MainHeroView mainHeroView = new MainHeroView(gc);
        WallView temp = new WallView(gc);
        eventManager.subscribe(mainHeroView);
        eventManager.subscribe(temp);


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


    public static void main(String[] args) {
        launch(args);
    }
}
