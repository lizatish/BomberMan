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
import java.util.Arrays;
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

        final Long[] lastNanoTime = {System.nanoTime()};
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime[0]) / 1000000000.0;
                lastNanoTime[0] = currentNanoTime;

                // game logic
                mainHero.setVelocity(0, 0);
                if (input.contains("LEFT") && mainHero.getPositionX() - 1 > 0) {
                    boolean canMove = move("LEFT", walls, mainHero);
                    if (canMove) {
                        mainHero.addVelocity(-50, 0);
                    }

                } else if (input.contains("RIGHT") && mainHero.getPositionX() + FIELD_STEP + 1 < FIELD_WIDTH) {
                    boolean canMove = move("RIGHT", walls, mainHero);
                    if (canMove) {
                        mainHero.addVelocity(50, 0);
                    }

                } else if (input.contains("UP") && mainHero.getPositionY() - 1 > 0) {
                    boolean canMove = move("UP", walls, mainHero);
                    if (canMove) {
                        mainHero.addVelocity(0, -50);
                    }

                } else if (input.contains("DOWN") && mainHero.getPositionY() + FIELD_STEP + 1 < FIELD_HEIGHT) {

                    boolean canMove = move("DOWN", walls, mainHero);
                    if (canMove) {
                        mainHero.addVelocity(0, 50);
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


    public boolean move(String moveType, ArrayList<Wall> walls, MainHero mainHero) {
        ArrayList<Integer> dir = new ArrayList<>(Arrays.asList(0, 0));

        if (moveType.contains("LEFT") || moveType.contains("RIGHT")) {
            dir.set(0, 1);
        } else {
            dir.set(1, 1);
        }

        for (Wall wall : walls) {
            double wallPositionX = wall.getPositionX();
            double wallPositionY = wall.getPositionY();
            double heroPositionX = mainHero.getPositionX();
            double heroPositionY = mainHero.getPositionY();

            if (moveType.contains("LEFT") && wallPositionX > mainHero.getPositionX()) {
                continue;
            } else if (moveType.contains("RIGHT") && wallPositionX < mainHero.getPositionX()) {
                continue;
            } else if (moveType.contains("UP") && wallPositionX > mainHero.getPositionY()) {
                continue;
            } else if (moveType.contains("DOWN") && wallPositionX < mainHero.getPositionY()) {
                continue;
            }

            if (Math.abs(heroPositionX - wallPositionX) - dir.get(0) < FIELD_STEP - 2 * dir.get(1) &&
                    Math.abs(heroPositionY - wallPositionY) - dir.get(1) < FIELD_STEP - 2 * dir.get(0)) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
