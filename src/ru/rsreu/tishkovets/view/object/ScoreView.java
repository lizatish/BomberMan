package ru.rsreu.tishkovets.view.object;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import ru.rsreu.tishkovets.Settings;
import ru.rsreu.tishkovets.events.EventListener;
import ru.rsreu.tishkovets.events.data.EventData;
import ru.rsreu.tishkovets.events.data.ScoreEventData;

public class ScoreView implements EventListener {
    private static final int FONT_SIZE = 30;
    private static final String FONT_TYPE = "TimesRoman";
    private static final int positionX = (int) Settings.FIELD_WIDTH - 120;
    private static final int posotionY = 30;
    GraphicsContext gc;
    private Image image;

    public void setImage(String filename) {
        image = new Image(filename, 200, 50, false, false);
    }

    public ScoreView(GraphicsContext gcInit) {
        gc = gcInit;
        setImage("file:src/ru/rsreu/tishkovets/view/resources/wall.jpg");
    }

    public void render(EventData data) {
        ScoreEventData renderData = (ScoreEventData) data;
        gc.setFont(new javafx.scene.text.Font(FONT_TYPE, FONT_SIZE));
        gc.setFill(javafx.scene.paint.Color.WHITE);

        gc.clearRect(positionX - 100, posotionY - 20, 200, 50);
        gc.drawImage(image, positionX - 100, posotionY - 20);

        gc.fillText(
                String.format("Score: %s", renderData.getScore()),
                positionX,
                posotionY
        );

        gc.fillText(
                "F1 - save game   P - pause game",
                250,
                posotionY
        );
    }

    @Override
    public void update(EventData data) {
        render(data);
    }
}
