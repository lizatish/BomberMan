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

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Main extends Application {

   // private final double FIELD_WIDTH = 1260;
   // private final double FIELD_HEIGHT = 780;
    private final double FIELD_WIDTH = 300;
    private final double FIELD_HEIGHT = 300;
    private final double FIELD_STEP = 60;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("BomberMan");

        Group root = new Group();
        Scene theScene = new Scene(root);

        primaryStage.setScene(theScene);

        Canvas canvas = new Canvas(FIELD_WIDTH, FIELD_HEIGHT);
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
        mainHero.setPosition(0, 0);

        ArrayList<Wall> walls = new ArrayList<>();
        for (int i = 60; i < FIELD_HEIGHT; i += 2 * FIELD_STEP) {
            for (int j = 60; j < FIELD_WIDTH; j += 2 * FIELD_STEP) {
                Wall temp = new Wall(j, i);
                walls.add(temp);
            }
        }

        LongValue lastNanoTime = new LongValue(System.nanoTime());
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                lastNanoTime.value = currentNanoTime;

                // game logic
                mainHero.setVelocity(0, 0);
                if (input.contains("LEFT")) {
                    if (mainHero.getPositionX() - 1 > 0) {
                        boolean is_fall = false;
                        for (Wall wall : walls) {
                            double wallPositionX = wall.getPositionX();
                            double wallPositionY = wall.getPositionY();
                            double heroPositionX = mainHero.getPositionX();
                            double heroPositionY = mainHero.getPositionY();
                            if (wallPositionX > mainHero.getPositionX())
                                continue;

                            if (Math.abs(heroPositionX - wallPositionX) < FIELD_STEP - 4 &&
                                    Math.abs(heroPositionY - wallPositionY) < FIELD_STEP - 4) {
                                System.out.println(Math.abs(heroPositionX - wallPositionX));
                                System.out.println(Math.abs(heroPositionY - wallPositionY));
                                is_fall = true;
                                break;
                            }
                        }
                        if (!is_fall) {
                            mainHero.addVelocity(-50, 0);
                        }
                    }
                } else if (input.contains("RIGHT")) {
                    if (mainHero.getPositionX() + FIELD_STEP + 1 < FIELD_WIDTH) {
                        boolean is_fall = false;
                        for (Wall wall : walls) {
                            double wallPositionX = wall.getPositionX();
                            double wallPositionY = wall.getPositionY();
                            double heroPositionX = mainHero.getPositionX();
                            double heroPositionY = mainHero.getPositionY();
                            if (wallPositionX < mainHero.getPositionX())
                                continue;

                            if (Math.abs(heroPositionX - wallPositionX) < FIELD_STEP - 4 &&
                                    Math.abs(heroPositionY - wallPositionY) < FIELD_STEP - 4) {
                                System.out.println(Math.abs(heroPositionX - wallPositionX));
                                System.out.println(Math.abs(heroPositionY - wallPositionY));
                                is_fall = true;
                                break;
                            }
                        }
                        if (!is_fall) {
                            mainHero.addVelocity(50, 0);
                        }
                    }
                } else if (input.contains("UP")) {
                    if (mainHero.getPositionY() - 1 > 0) {

                        boolean is_fall = false;
                        for (Wall wall : walls) {
                            double wallPositionX = wall.getPositionX();
                            double wallPositionY = wall.getPositionY();
                            double heroPositionX = mainHero.getPositionX();
                            double heroPositionY = mainHero.getPositionY();
                            if (wallPositionY > mainHero.getPositionY())
                                continue;

                            if (Math.abs(heroPositionX - wallPositionX) < FIELD_STEP - 4 &&
                                    Math.abs(heroPositionY - wallPositionY) < FIELD_STEP - 4) {
                                System.out.println(Math.abs(heroPositionX - wallPositionX));
                                System.out.println(Math.abs(heroPositionY - wallPositionY));
                                is_fall = true;
                                break;
                            }
                        }
                        if (!is_fall) {
                            mainHero.addVelocity(0, -50);
                        }
                    }
                } else if (input.contains("DOWN")) {
                    if (mainHero.getPositionY() + FIELD_STEP + 1 < FIELD_HEIGHT) {

                        boolean is_fall = false;
                        for (Wall wall : walls) {
                            double wallPositionX = wall.getPositionX();
                            double wallPositionY = wall.getPositionY();
                            double heroPositionX = mainHero.getPositionX();
                            double heroPositionY = mainHero.getPositionY();
                            if (wallPositionY < mainHero.getPositionY())
                                continue;

                            if (Math.abs(heroPositionX - wallPositionX) < FIELD_STEP - 4 &&
                                    Math.abs(heroPositionY - wallPositionY) < FIELD_STEP - 4) {
                                System.out.println(Math.abs(heroPositionX - wallPositionX));
                                System.out.println(Math.abs(heroPositionY - wallPositionY));
                                is_fall = true;
                                break;
                            }
                        }
                        if (!is_fall) {
                            mainHero.addVelocity(0, 50);
                        }
                    }
                }
                mainHero.update(elapsedTime);

                gc.clearRect(0, 0, FIELD_WIDTH, FIELD_HEIGHT);

                mainHero.render(gc);
                for (Wall wall : walls) {
                    wall.render(gc);
                }
            }
        }.start();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
