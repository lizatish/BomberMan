package ru.rsreu.tishkovets.view.notification;


import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import ru.rsreu.tishkovets.Settings;

public class NewGameNotification {
    private static final String FONT_TYPE = "TimesRoman";

    public void render(GraphicsContext gc) {
        gc.setFont(new Font(FONT_TYPE, 128));
        gc.setFill(Color.BROWN);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(
                "Bombers",
                Settings.FIELD_WIDTH / 2,
                Settings.FIELD_HEIGHT / 2
        );
        gc.setFont(new Font(FONT_TYPE, 68));
        gc.fillText(
                "Press Enter for start new game \n Press F2 to continue saved game",
                Settings.FIELD_WIDTH / 2,
                Settings.FIELD_HEIGHT * 3 / 4
        );

    }

}
