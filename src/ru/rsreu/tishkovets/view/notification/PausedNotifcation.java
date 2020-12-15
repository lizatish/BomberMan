package ru.rsreu.tishkovets.view.notification;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import ru.rsreu.tishkovets.Settings;

public class PausedNotifcation {
    private static final String FONT_TYPE = "TimesRoman";

    public void render(GraphicsContext gc) {
        gc.clearRect(0, 0, Settings.FIELD_WIDTH, Settings.FIELD_HEIGHT);
        gc.setFont(new Font(FONT_TYPE, 128));
        gc.setFill(Color.BROWN);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(
                "Game paused...",
                Settings.FIELD_WIDTH / 2,
                Settings.FIELD_HEIGHT / 2
        );
        gc.setFont(new Font(FONT_TYPE, 68));
        gc.fillText(
                "Press P for continue game",
                Settings.FIELD_WIDTH / 2,
                Settings.FIELD_HEIGHT * 3 / 4
        );

    }

}
