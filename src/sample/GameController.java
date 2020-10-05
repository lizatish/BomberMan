package sample;

import javafx.animation.AnimationTimer;
import sample.move.MovableEventType;

import java.util.ArrayList;

public class GameController {
    private final Bombers model;

    public GameController(Bombers model) {
        this.model = model;
    }

    public void start() {
//        model.start();
    }

    public void startMoving(MovableEventType movableEventType) {
        movableEventType.startMoving(model.getMainHero());
        System.out.println(111111111);
    }

//    public void stopMoving(MovableEventType movableEventType) {
//        movableEventType.stopMoving(model.getMainHero());
//    }

    public void checkUserChanges(ArrayList<String> input) {
        final Long[] lastNanoTime = {System.nanoTime()};
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime[0]) / 1000000000.0;
                lastNanoTime[0] = currentNanoTime;

                model.getMainHero().stop();
                MovableEventType eventType = MovableEventType.getMovableOperationByKeyName(input);
                if (eventType != null) {
                    boolean canMove = eventType.canMove(model.getWalls(), model.getMainHero());
                    if (canMove) {
                        eventType.startMoving(model.getMainHero());
                    }
                }

                model.getMainHero().update(elapsedTime);
            }
        }.start();
    }

}
