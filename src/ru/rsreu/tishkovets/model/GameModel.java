package ru.rsreu.tishkovets.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javafx.util.Pair;
import ru.rsreu.tishkovets.Settings;
import ru.rsreu.tishkovets.events.EventManager;
import ru.rsreu.tishkovets.events.EventType;
import ru.rsreu.tishkovets.events.MovableEventType;
import ru.rsreu.tishkovets.events.data.BaseEventData;
import ru.rsreu.tishkovets.events.data.ExplosionsEventData;
import ru.rsreu.tishkovets.events.data.InitEventData;
import ru.rsreu.tishkovets.events.data.PersonEventData;
import ru.rsreu.tishkovets.events.data.object.BaseData;
import ru.rsreu.tishkovets.events.data.object.PersonData;
import ru.rsreu.tishkovets.model.gameobjects.BaseObject;
import ru.rsreu.tishkovets.model.gameobjects.Box;
import ru.rsreu.tishkovets.model.gameobjects.MainHero;
import ru.rsreu.tishkovets.model.gameobjects.Wall;
import ru.rsreu.tishkovets.model.gameobjects.bomb.Bomb;
import ru.rsreu.tishkovets.model.gameobjects.bomb.Explosion;
import ru.rsreu.tishkovets.model.gameobjects.enemy.ArtificialIntelligence;
import ru.rsreu.tishkovets.model.gameobjects.enemy.Enemy;

public class GameModel implements GameAction {

    private final EventManager eventManager;
    private static GameState gameState = GameState.NEW;

    private final MainHero mainHero;
    private final List<Wall> walls = new ArrayList<>();
    private final List<Box> boxes = new CopyOnWriteArrayList<>();
    private final List<Bomb> bombs = new ArrayList<>();
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Explosion> explosions = new CopyOnWriteArrayList<>();

    private int bombCount = 1;

    public GameModel(EventManager eventManager) {
        this.eventManager = eventManager;

        mainHero = new MainHero(Settings.OBJECT_SIZE, Settings.OBJECT_SIZE,
                Settings.OBJECT_SIZE - 4, eventManager);

        generateWallsAndBoxes();
        generateEnemyes();
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

    @Override
    public void pause(boolean isPaused) {
        if (isPaused) {
            gameState = GameState.PAUSED;
        } else {
            gameState = GameState.RUNNING;
            update(EventType.INIT_UPDATE);
        }
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

    public synchronized void placeBomb() {
        if (bombCount > 0) {
            bombCount--;

            double horizontalStep = getHorizontalStep();
            double bombPositionX = mainHero.getPositionX() - mainHero.getPositionX() % horizontalStep;
            double bombPositionY = mainHero.getPositionY() - mainHero.getPositionY() % Settings.OBJECT_SIZE;
            double bombSize = mainHero.getSize();

            Bomb bomb = new Bomb(bombPositionX, bombPositionY, bombSize, eventManager, this);
            bombs.add(bomb);
            Thread thread = new Thread(bomb);
            thread.start();
        }
    }

    public boolean canEnemyMove(MovableEventType eventType, Enemy enemy) {
        Rectangle enemyRect = new Rectangle((int) enemy.getPositionX(), (int) enemy.getPositionY(),
                (int) enemy.getSize(), (int) enemy.getSize());

        return checkOutsideWallsCollision(enemyRect, enemy.getSpeed(), eventType) &&
                checkInsideWallsCollision(enemyRect) &&
                checkBombsCollision(enemyRect, enemyRect);
    }

    public void update(EventType eventType) {
        if (eventType == EventType.INIT_UPDATE) {
            eventManager.notify(eventType, createInitData());
        }
    }

    public synchronized void removeBoxesInCollision(BaseObject object) {
        Rectangle objectRect = new Rectangle((int) object.getPositionX(), (int) object.getPositionY(),
                (int) object.getSize(), (int) object.getSize());
        for (Box box : boxes) {
            int boxPositionX = (int) box.getPositionX();
            int boxPositionY = (int) box.getPositionY();
            int boxSize = (int) box.getSize();
            Rectangle wallRect = new Rectangle(boxPositionX, boxPositionY, boxSize, boxSize);
            if (objectRect.intersects(wallRect)) {
                boxes.remove(box);
                eventManager.notify(EventType.BOX_DELETE, new BaseEventData(box.createBoxData()));
            }
        }
    }

    public synchronized void explodeBomb(Bomb bomb) {
        bombs.remove(bomb);
        explosion(bomb);
        bombCount += 1;
    }

    public synchronized void removeExplosion(Explosion explosion) {
        if (checkMainHeroOnDeath()) {
            setGameState(GameState.END);
            System.out.println("GAME OVER");
        }
        for (Enemy enemy : enemies) {
            if (checkEnemyOnDeath(enemy)) {
                removeEnemy(enemy);
            }
        }

        eventManager.notify(EventType.EXPLOSION_REMOVE, new ExplosionsEventData(createExplosionData(true)));
        explosions.remove(explosion);
    }

    public static void setGameState(GameState gameState_) {
        gameState = gameState_;
    }

    public InitEventData createInitData() {
        return new InitEventData(mainHero.createMainHeroData(), createWallsData(),
                createEnemyesData(), createBoxesData());
    }

    public synchronized void removeEnemy(Enemy enemy) {
        eventManager.notify(EventType.ENEMY_REMOVE, new PersonEventData(enemy.createEnemyData(true)));
        enemies.remove(enemy);
        if (enemies.size() == 0) {
            setGameState(GameState.FINISHED);
        }
    }

    public boolean checkEnemyOnDeath(Enemy enemy) {
        Rectangle enemyRect = new Rectangle((int) enemy.getPositionX(),
                (int) enemy.getPositionY(), (int) enemy.getSize(), (int) enemy.getSize());

        for (Explosion explosion : explosions) {
            Rectangle explosionRect = new Rectangle((int) explosion.getPositionX(),
                    (int) explosion.getPositionY(), (int) explosion.getSize(), (int) explosion.getSize());
            if (enemyRect.intersects(explosionRect)) {
                return true;
            }
        }
        return false;
    }

    public synchronized boolean checkMainHeroOnDeath() {
        Rectangle mainHeroRect = new Rectangle((int) mainHero.getPositionX(),
                (int) mainHero.getPositionY(), (int) mainHero.getSize(), (int) mainHero.getSize());

        for (Explosion explosion : explosions) {
            Rectangle explosionRect = new Rectangle((int) explosion.getPositionX(),
                    (int) explosion.getPositionY(), (int) explosion.getSize(), (int) explosion.getSize());
            if (mainHeroRect.intersects(explosionRect)) {
                return true;
            }
        }

        for (Enemy enemy : enemies) {
            Rectangle enemyRect = new Rectangle((int) enemy.getPositionX(),
                    (int) enemy.getPositionY(), (int) enemy.getSize(), (int) enemy.getSize());
            if (mainHeroRect.intersects(enemyRect)) {
                return true;
            }
        }
        return false;
    }

    private synchronized void explosion(Bomb bomb) {
        int bombPositionX = (int) bomb.getPositionX();
        int bombPositionY = (int) bomb.getPositionY();
        int bombSize = (int) bomb.getSize();

        for (double i = Settings.OBJECT_SIZE; i <= Settings.EXPLOSION_STRANGE * Settings.OBJECT_SIZE; i += Settings.OBJECT_SIZE) {
            createExplosionBeam(bombPositionX + i, bombPositionY, bombSize);
            createExplosionBeam(bombPositionX, bombPositionY + i, bombSize);
            createExplosionBeam(bombPositionX - i, bombPositionY, bombSize);
            createExplosionBeam(bombPositionX, bombPositionY - i, bombSize);
        }

        createExplosion(bombPositionX, bombPositionY);
        eventManager.notify(EventType.EXPLOSION_UPDATE, new ExplosionsEventData(createExplosionData(false)));
    }

    private void createExplosionBeam(double explosionPositionX, double explosionPositionY, int bombSize) {
        Rectangle explosionRect = new Rectangle((int) explosionPositionX, (int) explosionPositionY, bombSize, bombSize);
        if (checkInsideWallsCollision(explosionRect)) {
            createExplosion(explosionPositionX, explosionPositionY);
        }
    }

    private synchronized List<BaseData> createExplosionData(boolean isDelete) {
        List<BaseData> explosionData = new ArrayList<>();
        for (Explosion explosion : explosions) {
            BaseData temp = new BaseData(explosion.getPositionX(), explosion.getPositionY(), explosion.getSize(), isDelete);
            explosionData.add(temp);
        }
        return explosionData;
    }

    private void startModel() {
        for (Enemy enemy : enemies) {
            Thread thread = new Thread(enemy);
            thread.start();
        }
    }

    private void createExplosion(double x, double y) {
        Explosion explosion = new Explosion(x, y, Settings.OBJECT_SIZE, this);
        if (explosions.contains(explosion))
            return;
        explosions.add(explosion);
        Thread thread = new Thread(explosion);
        thread.start();
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
            enemyCoordinates.add(new Pair<>(Settings.FIELD_WIDTH - 2 * Settings.OBJECT_SIZE,
                    Settings.FIELD_HEIGHT - 2 * Settings.OBJECT_SIZE));
            if (Settings.ENEMYES_NUMBER >= 2) {
                enemyCoordinates.add(new Pair<>(Settings.FIELD_WIDTH - 2 * Settings.OBJECT_SIZE,
                        Settings.OBJECT_SIZE));
                if (Settings.ENEMYES_NUMBER >= 3) {
                    enemyCoordinates.add(new Pair<>(Settings.OBJECT_SIZE, Settings.FIELD_HEIGHT - 2 * Settings.OBJECT_SIZE));
                }
            }
        }

        for (Pair<Double, Double> coordinates : enemyCoordinates) {
            enemies.add(new Enemy(coordinates.getKey(), coordinates.getValue(),
                    Settings.OBJECT_SIZE - 4,
                    new ArtificialIntelligence(this), this, eventManager));
        }
    }

    private void createWalls() {
        double horizontalStep = getHorizontalStep();

        for (int i = 0; i < Settings.FIELD_WIDTH; i += horizontalStep) {
            Wall temp = new Wall(i, 0, Settings.OBJECT_SIZE);
            walls.add(temp);
            temp = new Wall(i, Settings.FIELD_HEIGHT - Settings.OBJECT_SIZE, Settings.OBJECT_SIZE);
            walls.add(temp);
        }

        for (int j = 0; j < Settings.FIELD_HEIGHT; j += Settings.OBJECT_SIZE) {
            Wall temp = new Wall(0, j, Settings.OBJECT_SIZE);
            walls.add(temp);
            temp = new Wall(Settings.FIELD_WIDTH - Settings.OBJECT_SIZE, j, Settings.OBJECT_SIZE);
            walls.add(temp);
        }

        for (double i = 2 * horizontalStep; i < Settings.FIELD_WIDTH; i += 2 * horizontalStep) {
            for (double j = 2 * Settings.OBJECT_SIZE; j < Settings.FIELD_HEIGHT; j += 2 * Settings.OBJECT_SIZE) {
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
            } else if (x > Settings.FIELD_WIDTH - 3 * Settings.OBJECT_SIZE && y > Settings.FIELD_HEIGHT - 3 * Settings.OBJECT_SIZE) {
                continue;
            } else if (x < 3 * Settings.OBJECT_SIZE && y > Settings.FIELD_HEIGHT - 3 * Settings.OBJECT_SIZE) {
                continue;
            } else if (x > Settings.FIELD_WIDTH - 3 * Settings.OBJECT_SIZE && y < 3 * Settings.OBJECT_SIZE) {
                continue;
            }
            Box newBox = new Box(x, y, Settings.OBJECT_SIZE);
            if (!walls.contains(new Wall(x, y, Settings.OBJECT_SIZE)) && !boxes.contains(newBox)) {
                boxes.add(newBox);
                currentBoxesNumber += 1;
            }
        }
    }

    private List<PersonData> createEnemyesData() {
        List<PersonData> enemyesData = new ArrayList<>();
        for (Enemy enemy : enemies) {
            enemyesData.add(enemy.createEnemyData(false));
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

    // TODO удалить и все что связано
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

            if (mainHeroPrevRect != mainHeroRect && mainHeroPrevRect.intersects(boxRect)) {
                continue;
            }

            if (mainHeroRect.intersects(boxRect)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkBoxesCollision(Rectangle personRect) {
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

    private int getRandomNumber(double min, double max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public MainHero getMainHero() {
        return mainHero;
    }

    public static GameState getGameState() {
        return gameState;
    }

}
