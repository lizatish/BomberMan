package ru.rsreu.tishkovets.model;

import ru.rsreu.tishkovets.Settings;
import ru.rsreu.tishkovets.events.MovableEventType;
import ru.rsreu.tishkovets.events.EventType;
import ru.rsreu.tishkovets.events.data.InitEventData;
import ru.rsreu.tishkovets.events.data.object.BombData;
import ru.rsreu.tishkovets.events.data.object.BoxData;
import ru.rsreu.tishkovets.events.data.ModelUpdateEventData;
import ru.rsreu.tishkovets.events.EventManager;
import ru.rsreu.tishkovets.events.data.object.MainHeroData;
import ru.rsreu.tishkovets.events.data.object.WallData;
import ru.rsreu.tishkovets.model.gameobjects.Bomb;
import ru.rsreu.tishkovets.model.gameobjects.Box;
import ru.rsreu.tishkovets.model.gameobjects.MainHero;
import ru.rsreu.tishkovets.model.gameobjects.Wall;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameModel implements GameAction {
    private final EventManager eventManager;
    private volatile static GameState gameState = GameState.NEW;

    private final MainHero mainHero = new MainHero(0, 0, Settings.OBJECT_SIZE - 4, this);
    private final List<Wall> walls = new ArrayList<>();
    private final List<Box> boxes = new ArrayList<>();
    private final List<Bomb> bombs = new ArrayList<>();

    public GameModel(EventManager eventManager) {
        this.eventManager = eventManager;
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

        } else if (eventType == EventType.MODEL_UPDATE) {
            eventManager.notify(eventType, new ModelUpdateEventData(createMainHeroData(), createBombsData()));
        }
    }

    @Override
    public void placeBomb() {

        double bombPositionX = mainHero.getPositionX();
        double bombPositionY = mainHero.getPositionY();
        double bombSize = mainHero.getSize();

        Bomb bomb = new Bomb(bombPositionX, bombPositionY, bombSize, this);
        bombs.add(bomb);

        update(EventType.MODEL_UPDATE);

    }

    @Override
    public void pause(boolean isPaused) {
        if (isPaused) {
            gameState = GameState.PAUSED;
        } else {
            gameState = GameState.RUNNING;
        }
        update(EventType.MODEL_UPDATE);
    }

    @Override
    public boolean isPaused() {
        return false;
    }

    @Override
    public void start() {

    }

    private double getHorizontalStep() {
        double horizontalStep = Settings.FIELD_WIDTH / (2 * Settings.WALLS_NUMBER_IN_ROW + 1);
        if (horizontalStep < Settings.OBJECT_SIZE) {
            horizontalStep = Settings.OBJECT_SIZE;
        }
        return horizontalStep;
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
            if (x < 2 * Settings.OBJECT_SIZE && 2 * y < Settings.OBJECT_SIZE ||
                    x > Settings.FIELD_WIDTH - 2 * Settings.OBJECT_SIZE
                            && y > Settings.FIELD_HEIGHT - 2 * Settings.OBJECT_SIZE) {
                continue;
            }

            if (!walls.contains(new Wall(x, y, Settings.OBJECT_SIZE))) {
                boxes.add(new Box(x, y, Settings.OBJECT_SIZE));
                currentBoxesNumber += 1;
            }
        }
    }

    private List<BombData> createBombsData() {
        List<BombData> bombData = new ArrayList<>();
        for (Bomb bomb : bombs) {
            BombData temp = new BombData(bomb.getPositionX(), bomb.getPositionY(), bomb.getSize());
            bombData.add(temp);
        }
        return bombData;
    }

    private MainHeroData createMainHeroData() {
        return new MainHeroData(mainHero.getPositionX(), mainHero.getPositionY(),
                mainHero.getPrevPositionX(), mainHero.getPrevPositionY(), mainHero.getSize());
    }

    private InitEventData createInitData() {
        List<WallData> wallsData = new ArrayList<>();
        for (Wall wall : walls) {
            WallData temp = new WallData(wall.getPositionX(), wall.getPositionY());
            wallsData.add(temp);
        }
        List<BoxData> boxesData = new ArrayList<>();
        for (Box box : boxes) {
            BoxData temp = new BoxData(box.getPositionX(), box.getPositionY());
            boxesData.add(temp);
        }
        MainHeroData mainHeroData = new MainHeroData(mainHero.getPositionX(), mainHero.getPositionY(),
                mainHero.getPrevPositionX(), mainHero.getPrevPositionY(), mainHero.getSize());

        return new InitEventData(mainHeroData, wallsData, boxesData);
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
    private boolean  checkBombsCollision(Rectangle mainHeroRect) {
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

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Box> getBoxes() {
        return boxes;
    }
}
