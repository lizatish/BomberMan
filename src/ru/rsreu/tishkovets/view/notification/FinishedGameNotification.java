package ru.rsreu.tishkovets.view.notification;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import ru.rsreu.tishkovets.Settings;
import ru.rsreu.tishkovets.events.EventListener;
import ru.rsreu.tishkovets.events.data.EventData;
import ru.rsreu.tishkovets.events.data.ScoreEventData;
import ru.rsreu.tishkovets.model.GameModel;

public class FinishedGameNotification implements EventListener {
    private static final String FONT_TYPE = "TimesRoman";
    private final GraphicsContext gc;

    public FinishedGameNotification(GraphicsContext gc) {
        this.gc = gc;
    }

    public void render(EventData data) {
        ScoreEventData renderData = (ScoreEventData) data;
        gc.clearRect(0, 0, Settings.FIELD_WIDTH, Settings.FIELD_HEIGHT);

        gc.setFont(new javafx.scene.text.Font(FONT_TYPE, 128));
        gc.setFill(javafx.scene.paint.Color.BROWN);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(
                "Bombers",
                Settings.FIELD_WIDTH / 2,
                Settings.FIELD_HEIGHT / 2
        );
        gc.setFont(new javafx.scene.text.Font(FONT_TYPE, 68));
        gc.fillText(
                String.format("Congratulations! You won! \nYour score is %d", renderData.getScore()),
                Settings.FIELD_WIDTH / 2,
                Settings.FIELD_HEIGHT * 3 / 4
        );
    }

    @Override
    public void update(EventData data) {
        render(data);
    }
}
