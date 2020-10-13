package sample.Controller;

import javafx.animation.AnimationTimer;
import sample.Model.Bombers;
import sample.Controller.move.MovableEventType;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private final Bombers model;

    public GameController(Bombers model) {
        this.model = model;
    }

    public void move(MovableEventType eventType) {
        boolean canMove = eventType.canMove(model.getWalls(), model.getMainHero());
        if (canMove) {
            eventType.startMoving(model.getMainHero());
        }
        model.getMainHero().stop();
    }
}
