package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import sample.gameobjects.MainHero;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("BomberMan");

        Group root = new Group();
        Scene theScene = new Scene(root);

        primaryStage.setScene(theScene);

        Canvas canvas = new Canvas(800, 800);
        root.getChildren().add(canvas);

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

        GraphicsContext gc = canvas.getGraphicsContext2D();
        MainHero mainHero = new MainHero();
        mainHero.setImage("file:/home/liza/IdeaProjects/MVC_/src/sample/resources/mainHero.png");
        mainHero.setPosition(50, 50);


        LongValue lastNanoTime = new LongValue(System.nanoTime());


        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                lastNanoTime.value = currentNanoTime;

                // game logic

                mainHero.setVelocity(0, 0);
                if (input.contains("LEFT"))
                    mainHero.addVelocity(-50, 0);
                if (input.contains("RIGHT"))
                    mainHero.addVelocity(50, 0);
                if (input.contains("UP"))
                    mainHero.addVelocity(0, -50);
                if (input.contains("DOWN"))
                    mainHero.addVelocity(0, 50);

                mainHero.update(elapsedTime);

                gc.clearRect(0, 0, 512, 512);
                mainHero.render(gc);
            }
        }.start();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
