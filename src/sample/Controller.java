package sample;

import javafx.animation.AnimationTimer;
import sample.gameobjects.MainHero;
import sample.gameobjects.Wall;

import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    private Model model;

    private final double FIELD_WIDTH = 1260; // TODO: Это не тут
    private final double FIELD_HEIGHT = 780;
    private final double FIELD_STEP = 60;

    public Controller(Model model) {
        this.model = model;
    }

    public void start() {
//        model.start();
    }
    public void checkUserChanges(ArrayList<String> input) {
        final Long[] lastNanoTime = {System.nanoTime()};
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime[0]) / 1000000000.0;
                lastNanoTime[0] = currentNanoTime;

                // game logic
                model.mainHero.setVelocity(0, 0);
                if (input.contains("LEFT") && model.mainHero.getPositionX() - 1 > 0) {
                    boolean canMove = move("LEFT", model.walls, model.mainHero);
                    if (canMove) {
                        System.out.println("L");
                        model.mainHero.addVelocity(-50, 0);
                    }
                } else if (input.contains("RIGHT") && model.mainHero.getPositionX() + FIELD_STEP + 1 < FIELD_WIDTH) {
                    boolean canMove = move("RIGHT", model.walls, model.mainHero);
                    if (canMove) {
                        System.out.println("R");
                        model.mainHero.addVelocity(50, 0);
                    }
                } else if (input.contains("UP") && model.mainHero.getPositionY() - 1 > 0) {
                    boolean canMove = move("UP", model.walls, model.mainHero);
                    if (canMove) {
                        System.out.println("U");
                        model.mainHero.addVelocity(0, -50);
                    }
                } else if (input.contains("DOWN") && model.mainHero.getPositionY() + FIELD_STEP + 1 < FIELD_HEIGHT) {
                    boolean canMove = move("DOWN", model.walls, model.mainHero);
                    if (canMove) {
                        System.out.println("D");
                        model.mainHero.addVelocity(0, 50);
                    }
                }
                model.mainHero.update(elapsedTime);
            }
        }.start();
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


}
