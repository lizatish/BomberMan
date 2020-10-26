package ru.rsreu.tishkovets.view;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.rsreu.tishkovets.Settings;
import ru.rsreu.tishkovets.controller.GameController;
import ru.rsreu.tishkovets.events.GameEventType;
import ru.rsreu.tishkovets.events.MovableEventType;
import ru.rsreu.tishkovets.events.EventManager;
import ru.rsreu.tishkovets.events.EventType;
import ru.rsreu.tishkovets.view.notification.NewGameNotification;
import ru.rsreu.tishkovets.view.object.BombsView;
import ru.rsreu.tishkovets.view.object.BoxesView;
import ru.rsreu.tishkovets.view.object.MainHeroView;
import ru.rsreu.tishkovets.view.object.WallsView;

import java.util.ArrayList;
import java.util.List;

public class GameView {
    private List<String> keyboardInput;
    private GraphicsContext gc;
    private final GameController controller;
    private final EventManager eventManager;
    private boolean isPaintGame = false;

    public GameView(GameController controller, EventManager eventManager) {
        this.controller = controller;
        this.eventManager = eventManager;
    }

    public void start(Stage primaryStage) {
        initializeGameSceneAndKeyboard(primaryStage);

        MainHeroView mainHeroView = new MainHeroView(gc);
        WallsView wallsView = new WallsView(gc);
        BoxesView boxesView = new BoxesView(gc);
        BombsView bombsView = new BombsView(gc);

        eventManager.subscribe(EventType.MODEL_UPDATE, mainHeroView);
        eventManager.subscribe(EventType.MODEL_UPDATE, bombsView);

        eventManager.subscribe(EventType.INIT_UPDATE, mainHeroView);
        eventManager.subscribe(EventType.INIT_UPDATE, wallsView);
        eventManager.subscribe(EventType.INIT_UPDATE, boxesView);

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                GameEventType gameEventType = GameEventType.getGameEventTypeByKeyName(keyboardInput);
                if (gameEventType != null) {
                    if (gameEventType == GameEventType.START) {
                        isPaintGame = true;
                        gc.clearRect(0, 0, Settings.FIELD_WIDTH, Settings.FIELD_HEIGHT);
                    } else if (gameEventType == GameEventType.PAUSE
                    ) {
                        isPaintGame = false;
                    }
                    controller.startAction(gameEventType);
                }

                if (!isPaintGame) {
                    NewGameNotification notification = new NewGameNotification();
                    notification.render(gc);
                } else {
                    MovableEventType eventType = MovableEventType.getMovableOperationByKeyName(keyboardInput);
                    if (eventType != null) {
                        controller.move(eventType);
                    }
                }
            }
        }.start();
    }

    private void initializeGameSceneAndKeyboard(Stage primaryStage) {
        primaryStage.setTitle("Bombers");
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

}
