package ru.rsreu.tishkovets.model;

import javafx.util.Pair;
import ru.rsreu.tishkovets.Settings;
import ru.rsreu.tishkovets.events.EventManager;
import ru.rsreu.tishkovets.events.EventType;
import ru.rsreu.tishkovets.events.MovableEventType;
import ru.rsreu.tishkovets.events.data.BaseEventData;
import ru.rsreu.tishkovets.events.data.ExplosionsEventData;
import ru.rsreu.tishkovets.events.data.InitEventData;
import ru.rsreu.tishkovets.events.data.object.BaseData;
import ru.rsreu.tishkovets.events.data.object.PersonData;
import ru.rsreu.tishkovets.model.gameobjects.bomb.Bomb;
import ru.rsreu.tishkovets.model.gameobjects.Box;
import ru.rsreu.tishkovets.model.gameobjects.MainHero;
import ru.rsreu.tishkovets.model.gameobjects.Wall;
import ru.rsreu.tishkovets.model.gameobjects.bomb.Explosion;
import ru.rsreu.tishkovets.model.gameobjects.enemy.ArtificialIntelligence;
import ru.rsreu.tishkovets.model.gameobjects.enemy.Enemy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameModel implements GameAction {
    private final EventManager eventManager;
    private volatile static GameState gameState = GameState.NEW;

    private final MainHero mainHero;
    private final List<Wall> walls = new ArrayList<>();
    private final List<Box> boxes = new CopyOnWriteArrayList<>();
    private final List<Bomb> bombs = new ArrayList<>();
    private final List<Enemy> enemyes = new ArrayList<>();
    private final List<Explosion> explosions = new CopyOnWriteArrayList<>();

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

    public boolean canMainHeroMove(MovableEventType eventType) {
        Rectangle mainHeroRect = new Rectangle((int) mainHero.getPositionX(), (int) mainHero.getPositionY(),
                (int) mainHero.getSize(), (int) mainHero.getSize());
        Rectangle mainHeroPrevRect = new Rectangle((int) mainHero.getPrevPositionX(),
                (int) mainHero.getPrevPositionY(), (int) mainHero.getSize(), (int) mainHero.getSize());

        return checkOutsideWallsCollision(mainHeroRect, mainHero.getSpeed(), eventType) &&
                checkBoxesCollision(mainHeroRect) && checkInsideWallsCollision(mainHeroRect)
                && checkBombsCollision(mainHeroRect, mainHeroPrevRect);
    }

    public boolean canEnemyMove(MovableEventType eventType, Enemy enemy) {
        Rectangle enemyRect = new Rectangle((int) enemy.getPositionX(), (int) enemy.getPositionY(),
                (int) enemy.getSize(), (int) enemy.getSize());

        return checkOutsideWallsCollision(enemyRect, enemy.getSpeed(), eventType) &&
                checkInsideWallsCollision(enemyRect) && checkBombsCollision(enemyRect, enemyRect);
    }

    private void startModel() {
//        Thread thread = new Thread(mainHero);
//        thread.start();
        for (Enemy enemy : enemyes) {
            Thread thread = new Thread(enemy);
            thread.start();
        }
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

        Bomb bomb = new Bomb(bombPositionX, bombPositionY, bombSize, eventManager, this);
        bombs.add(bomb);
        Thread thread = new Thread(bomb);
        thread.start();
        //        }

    }

    public void explodeBomb(Bomb bomb) {
        explosion(bomb);
        bombs.remove(bomb);
    }

    public void removeExplosion(Explosion explosion) {
        explosions.remove(explosion);
    }

    private synchronized void explosion(Bomb bomb) {
        int bombPositionX = (int) bomb.getPositionX();
        int bombPositionY = (int) bomb.getPositionY();
        int bombSize = (int) bomb.getSize();

        for (double i = Settings.OBJECT_SIZE; i <= Settings.EXPLOSION_STRANGE * Settings.OBJECT_SIZE; i += Settings.OBJECT_SIZE) {
            Rectangle explosionRect = new Rectangle((int) (bombPositionX + i), bombPositionY, bombSize, bombSize);
            if (checkInsideWallsCollision(explosionRect)) {
                createExplosion(bombPositionX + i, bombPositionY);
            } else {
                break;
            }
        }
        for (double i = Settings.OBJECT_SIZE; i <= Settings.EXPLOSION_STRANGE * Settings.OBJECT_SIZE; i += Settings.OBJECT_SIZE) {
            Rectangle explosionRect = new Rectangle(bombPositionX, (int) (bombPositionY + i), bombSize, bombSize);
            if (checkInsideWallsCollision(explosionRect)) {
                createExplosion(bombPositionX, bombPositionY + i);
            } else {
                break;
            }
        }
        for (double i = Settings.OBJECT_SIZE; i <= Settings.EXPLOSION_STRANGE * Settings.OBJECT_SIZE; i += Settings.OBJECT_SIZE) {
            Rectangle explosionRect = new Rectangle((int) (bombPositionX - i), bombPositionY, bombSize, bombSize);
            if (checkInsideWallsCollision(explosionRect)) {
                createExplosion(bombPositionX - i, bombPositionY);
            } else {
                break;
            }
        }
        for (double i = Settings.OBJECT_SIZE; i <= Settings.EXPLOSION_STRANGE * Settings.OBJECT_SIZE; i += Settings.OBJECT_SIZE) {
            Rectangle explosionRect = new Rectangle(bombPositionX, (int) (bombPositionY - i), bombSize, bombSize);
            if (checkInsideWallsCollision(explosionRect)) {
                createExplosion(bombPositionX, bombPositionY - i);
            } else {
                break;
            }
        }

        createExplosion(bombPositionX, bombPositionY);
        eventManager.notify(EventType.EXPLOSION_UPDATE, new ExplosionsEventData(createExplosionData()));
        System.out.println(explosions.size());
    }

    private List<BaseData> createExplosionData() {
        List<BaseData> explosionData = new ArrayList<>();
        for (Explosion explosion : explosions) {
            BaseData temp = new BaseData(explosion.getPositionX(), explosion.getPositionY(), explosion.getSize());
            explosionData.add(temp);
        }
        return explosionData;
    }

    private void createExplosion(double x, double y) {
        Explosion explosion = new Explosion(x, y, Settings.OBJECT_SIZE, eventManager, this);
        if (explosions.contains(explosion))
            return;
        explosions.add(explosion);
        Thread thread = new Thread(explosion);
        thread.start();
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
        startModel();
    }

    private double getHorizontalStep() {
        double horizontalStep = Settings.FIELD_WIDTH / (2 * Settings.WALLS_NUMBER_IN_ROW + 1);
        if (horizontalStep < Settings.OBJECT_SIZE) {
            horizontalStep = Settings.OBJECT_SIZE;
        }
        return horizontalStep;
    }

    private void generateEnemyes() {
        List<Pair<Double, Double>> enemyCoordinates = new ArrayList<>();

        if (Settings.ENEMYES_NUMBER >= 1) {
            enemyCoordinates.add(new Pair<>(Settings.FIELD_WIDTH - Settings.OBJECT_SIZE,
                    Settings.FIELD_HEIGHT - Settings.OBJECT_SIZE));
            if (Settings.ENEMYES_NUMBER >= 2) {
                enemyCoordinates.add(new Pair<>(Settings.FIELD_WIDTH - Settings.OBJECT_SIZE, 0.0));
                if (Settings.ENEMYES_NUMBER >= 3) {
                    enemyCoordinates.add(new Pair<>(0.0, Settings.FIELD_HEIGHT - Settings.OBJECT_SIZE));
                }
            }
        }

        for (Pair<Double, Double> coordinates : enemyCoordinates) {
            enemyes.add(new Enemy(coordinates.getKey(), coordinates.getValue(),
                    Settings.OBJECT_SIZE - 4,
                    new ArtificialIntelligence(this), eventManager));
        }
    }

    public void deleteWallIfEnemyCollision(Enemy enemy) {
        Rectangle enemyRect = new Rectangle((int) enemy.getPositionX(), (int) enemy.getPositionY(),
                (int) enemy.getSize(), (int) enemy.getSize());
        for (Box box : boxes) {
            int boxPositionX = (int) box.getPositionX();
            int boxPositionY = (int) box.getPositionY();
            int boxSize = (int) box.getSize();
            Rectangle wallRect = new Rectangle(boxPositionX, boxPositionY, boxSize, boxSize);
            if (enemyRect.intersects(wallRect)) {
                boxes.remove(box);
                eventManager.notify(EventType.BOX_DELETE, new BaseEventData(box.createBoxData()));
                return;
            }
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

    private List<BaseData> createWallsData() {
        List<BaseData> wallsData = new ArrayList<>();
        for (Wall wall : walls) {
            BaseData temp = new BaseData(wall.getPositionX(), wall.getPositionY(), wall.getSize());
            wallsData.add(temp);
        }
        return wallsData;
    }

    private List<BaseData> createBoxesData() {
        List<BaseData> boxesData = new ArrayList<>();
        for (Box box : boxes) {
            BaseData temp = new BaseData(box.getPositionX(), box.getPositionY(), box.getSize());
            boxesData.add(temp);
        }
        return boxesData;
    }

    public InitEventData createInitData() {
        return new InitEventData(mainHero.createMainHeroData(), createWallsData(),
                createEnemyesData(), createBoxesData());
    }

    private boolean checkOutsideWallsCollision(Rectangle person, double speed, MovableEventType eventType) {
        if (eventType == MovableEventType.UP) {
            person.y -= speed;
            return person.y >= 0;
        } else if (eventType == MovableEventType.DOWN) {
            person.y += speed;
            return !(person.y + person.height > Settings.FIELD_HEIGHT);
        } else if (eventType == MovableEventType.LEFT) {
            person.x -= speed;
            return person.x >= 0;
        } else if (eventType == MovableEventType.RIGHT) {
            person.x += speed;
            return !(person.x + person.height > Settings.FIELD_WIDTH);
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

    private boolean checkBombsCollision(Rectangle mainHeroRect, Rectangle mainHeroPrevRect) {
        for (Bomb bomb : bombs) {
            int boxPositionX = (int) bomb.getPositionX();
            int boxPositionY = (int) bomb.getPositionY();
            int boxSize = (int) bomb.getSize();
            Rectangle boxRect = new Rectangle(boxPositionX, boxPositionY, boxSize, boxSize);

            if (mainHeroPrevRect.intersects(boxRect)) {
                continue;
            }

            if (mainHeroRect.intersects(boxRect)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkBoxesCollision(Rectangle personRect) {
        for (Box box : boxes) {
            int boxPositionX = (int) box.getPositionX();
            int boxPositionY = (int) box.getPositionY();
            int boxSize = (int) box.getSize();
            Rectangle wallRect = new Rectangle(boxPositionX, boxPositionY, boxSize, boxSize);

            if (personRect.intersects(wallRect)) {
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

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public List<Bomb> getBombs() {
        return bombs;
    }

    public List<Enemy> getEnemyes() {
        return enemyes;
    }
}
