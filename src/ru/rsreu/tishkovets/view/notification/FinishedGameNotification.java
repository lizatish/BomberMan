package ru.rsreu.tishkovets.view.notification;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import ru.rsreu.tishkovets.Settings;

public class FinishedGameNotification {
    private static final String FONT_TYPE = "TimesRoman";

    public void render(GraphicsContext gc) {

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
                "Congratulations! You are win! \nYour score is ",
                Settings.FIELD_WIDTH / 2,
                Settings.FIELD_HEIGHT * 3 / 4
        );
    }

}
