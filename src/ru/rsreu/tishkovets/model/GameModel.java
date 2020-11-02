package ru.rsreu.tishkovets.model;

import ru.rsreu.tishkovets.Settings;
import ru.rsreu.tishkovets.events.MovableEventType;
import ru.rsreu.tishkovets.events.EventType;
import ru.rsreu.tishkovets.events.data.*;
import ru.rsreu.tishkovets.events.data.object.StaticObjectData;
import ru.rsreu.tishkovets.events.EventManager;
import ru.rsreu.tishkovets.events.data.object.PersonData;
import ru.rsreu.tishkovets.model.gameobjects.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameModel implements GameAction {
    private final EventManager eventManager;
    private volatile static GameState gameState = GameState.NEW;

    private final MainHero mainHero;
    private final List<Wall> walls = new ArrayList<>();
    private final List<Box> boxes = new ArrayList<>();
    private final List<Bomb> bombs = new ArrayList<>();
    private final List<Enemy> enemyes = new ArrayList<>();

    private int bombCount;

    public GameModel(EventManager eventManager) {
        this.eventManager = eventManager;

        mainHero = new MainHero(0, 0, Settings.OBJECT_SIZE - 4, eventManager);

        generateEnemyes();
        generateWallsAndBoxes();
    }

    public void generateWallsAndBoxes() {
        createWalls();
        createBoxes();
    }

    public boolean canMove(MovableEventType eventType) {
        Rectangle mainHeroRect = new Rectangle((int) mainHero.getPositionX(), (int) mainHero.getPositionY(),
                (int) mainHero.getSize(), (int) mainHero.getSize());

        return checkOutsideWallsCollision(mainHeroRect, mainHero.getSpeed(), eventType) &&
                checkBoxesCollision(mainHeroRect) && checkInsideWallsCollision(mainHeroRect);
    }


    public void update(EventType eventType) {
        if (eventType == EventType.INIT_UPDATE) {
            eventManager.notify(eventType, createInitData());
        }
    }

    @Override
    public void placeBomb() {
//        if (bombCount != 0) {
//            bombCount--;
        double bombPositionX = mainHero.getPositionX();
        double bombPositionY = mainHero.getPositionY();
        double bombSize = mainHero.getSize();

        Bomb bomb = new Bomb(bombPositionX, bombPositionY, bombSize, eventManager);
        bombs.add(bomb);
        Thread thread = new Thread(bomb);
        thread.start();
        //        }

    }

    @Override
    public void pause(boolean isPaused) {
        if (isPaused) {
            gameState = GameState.PAUSED;
        } else {
            gameState = GameState.RUNNING;
            update(EventType.INIT_UPDATE);
        }
//        update(EventType.MODEL_UPDATE);
    }

    @Override
    public boolean isPaused() {
        return GameState.PAUSED.equals(gameState);
    }

    @Override
    public void start() {
        if (GameState.NEW == gameState) {
            gameState = GameState.RUNNING;
            update(EventType.INIT_UPDATE);
        }
    }

    private double getHorizontalStep() {
        double horizontalStep = Settings.FIELD_WIDTH / (2 * Settings.WALLS_NUMBER_IN_ROW + 1);
        if (horizontalStep < Settings.OBJECT_SIZE) {
            horizontalStep = Settings.OBJECT_SIZE;
        }
        return horizontalStep;
    }

    private void generateEnemyes() {
        if (Settings.ENEMYES_NUMBER == 1) {
            enemyes.add(new Enemy(Settings.FIELD_WIDTH - Settings.OBJECT_SIZE,
                    Settings.FIELD_HEIGHT - Settings.OBJECT_SIZE, Settings.OBJECT_SIZE, eventManager));
        } else if (Settings.ENEMYES_NUMBER == 2) {
            enemyes.add(new Enemy(Settings.FIELD_WIDTH - Settings.OBJECT_SIZE,
                    0, Settings.OBJECT_SIZE, eventManager));
            enemyes.add(new Enemy(Settings.FIELD_WIDTH - Settings.OBJECT_SIZE,
                    Settings.FIELD_HEIGHT - Settings.OBJECT_SIZE, Settings.OBJECT_SIZE, eventManager));
        } else {
            enemyes.add(new Enemy(Settings.FIELD_WIDTH - Settings.OBJECT_SIZE,
                    0, Settings.OBJECT_SIZE, eventManager));
            enemyes.add(new Enemy(Settings.FIELD_WIDTH - Settings.OBJECT_SIZE,
                    Settings.FIELD_HEIGHT - Settings.OBJECT_SIZE, Settings.OBJECT_SIZE, eventManager));
            enemyes.add(new Enemy(0, Settings.FIELD_HEIGHT - Settings.OBJECT_SIZE,
                    Settings.OBJECT_SIZE, eventManager));
        }
    }

    private void createWalls() {
        double horizontalStep = getHorizontalStep();
        for (double i = horizontalStep; i < Settings.FIELD_WIDTH; i += 2 * horizontalStep) {
            for (double j = Settings.OBJECT_SIZE; j < Settings.FIELD_HEIGHT; j += 2 * Settings.OBJECT_SIZE) {
                Wall temp = new Wall(i, j, Settings.OBJECT_SIZE);
                walls.add(temp);
            }
        }
    }

    private void createBoxes() {
        double horizontalStep = getHorizontalStep();
        int currentBoxesNumber = 0;
        while (currentBoxesNumber < Settings.BOXES_NUMBER) {
            int x = getRandomNumber(0, Settings.FIELD_WIDTH - 1);
            int y = getRandomNumber(0, Settings.FIELD_HEIGHT - 1);
            x -= x % horizontalStep;
            y -= y % Settings.OBJECT_SIZE;
            if (x < 3 * Settings.OBJECT_SIZE && y < 3 * Settings.OBJECT_SIZE) {
                continue;
            } else if (x > Settings.FIELD_WIDTH - 3 * Settings.OBJECT_SIZE
                    && y > Settings.FIELD_HEIGHT - 3 * Settings.OBJECT_SIZE) {
                continue;
            } else if (x < 3 * Settings.OBJECT_SIZE && y > Settings.FIELD_HEIGHT - 3 * Settings.OBJECT_SIZE) {
                continue;
            } else if (x > Settings.FIELD_WIDTH - 3 * Settings.OBJECT_SIZE && y < 3 * Settings.OBJECT_SIZE) {
                continue;
            }
            if (!walls.contains(new Wall(x, y, Settings.OBJECT_SIZE))) {
                boxes.add(new Box(x, y, Settings.OBJECT_SIZE));
                currentBoxesNumber += 1;
            }
        }
    }

    private List<PersonData> createEnemyesData() {
        List<PersonData> enemyesData = new ArrayList<>();
        for (Enemy enemy : enemyes) {
            enemyesData.add(enemy.createEnemyData());
        }
        return enemyesData;
    }

    private List<StaticObjectData> createWallsData() {
        List<StaticObjectData> wallsData = new ArrayList<>();
        for (Wall wall : walls) {
            StaticObjectData temp = new StaticObjectData(wall.getPositionX(), wall.getPositionY(), wall.getSize());
            wallsData.add(temp);
        }
        return wallsData;
    }

    private List<StaticObjectData> createBoxesData() {
        List<StaticObjectData> boxesData = new ArrayList<>();
        for (Box box : boxes) {
            StaticObjectData temp = new StaticObjectData(box.getPositionX(), box.getPositionY(), box.getSize());
            boxesData.add(temp);
        }
        return boxesData;
    }

    private InitEventData createInitData() {
        return new InitEventData(mainHero.createMainHeroData(), createWallsData(),
                createEnemyesData(), createBoxesData());
    }

    private boolean checkOutsideWallsCollision(Rectangle mainHeroRect, double mainHeroSpeed, MovableEventType eventType) {
        if (eventType == MovableEventType.UP) {
            mainHeroRect.y -= mainHeroSpeed;
            return mainHeroRect.y >= 0;
        } else if (eventType == MovableEventType.DOWN) {
            mainHeroRect.y += mainHeroSpeed;
            return !(mainHeroRect.y + mainHeroRect.height > Settings.FIELD_HEIGHT);
        } else if (eventType == MovableEventType.LEFT) {
            mainHeroRect.x -= mainHeroSpeed;
            return mainHeroRect.x >= 0;
        } else if (eventType == MovableEventType.RIGHT) {
            mainHeroRect.x += mainHeroSpeed;
            return !(mainHeroRect.x + mainHeroRect.height > Settings.FIELD_WIDTH);
        }
        return true;
    }

    private boolean checkInsideWallsCollision(Rectangle mainHeroRect) {
        for (Wall wall : walls) {
            int wallPositionX = (int) wall.getPositionX();
            int wallPositionY = (int) wall.getPositionY();
            int wallSize = (int) wall.getSize();
            Rectangle wallRect = new Rectangle(wallPositionX, wallPositionY, wallSize, wallSize);

            if (mainHeroRect.intersects(wallRect)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkBombsCollision(Rectangle mainHeroRect) {
        for (Bomb bomb : bombs) {
            int boxPositionX = (int) bomb.getPositionX();
            int boxPositionY = (int) bomb.getPositionY();
            int boxSize = (int) bomb.getSize();
            Rectangle wallRect = new Rectangle(boxPositionX, boxPositionY, boxSize, boxSize);

            if (mainHeroRect.intersects(wallRect)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkBoxesCollision(Rectangle mainHeroRect) {
        for (Box box : boxes) {
            int boxPositionX = (int) box.getPositionX();
            int boxPositionY = (int) box.getPositionY();
            int boxSize = (int) box.getSize();
            Rectangle wallRect = new Rectangle(boxPositionX, boxPositionY, boxSize, boxSize);

            if (mainHeroRect.intersects(wallRect)) {
                return false;
            }
        }
        return true;
    }

    public int getRandomNumber(double min, double max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public MainHero getMainHero() {
        return mainHero;
    }

    public static GameState getGameState() {
        return gameState;
    }

}
