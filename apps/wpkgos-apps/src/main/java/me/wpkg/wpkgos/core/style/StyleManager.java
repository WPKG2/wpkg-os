package me.wpkg.wpkgos.core.style;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

public class StyleManager {
    private static Style style;

    public static void setStyle(Style style){
        StyleManager.style = style;
    }

    static ArrayList<Scene> trackedScenes = new ArrayList<>();

    public static void init() {
//        style = Config.getSettings().theme.equals("DARK") ? Style.DARK : Style.LIGHT;
        style = Style.DARK;
    }

    public static void loadStyle(Scene scene){
        scene.getStylesheets().clear();
        for (String stylesheet : style.getStyles())
            scene.getStylesheets().add(Objects.requireNonNull(StyleManager.class.getResource(stylesheet)).toExternalForm());
    }

    public static void loadStyle(Parent pane) {
        pane.getStylesheets().clear();
        for (String stylesheet : style.getStyles())
            pane.getStylesheets().add(Objects.requireNonNull(StyleManager.class.getResource(stylesheet)).toExternalForm());
    }

    public static void trackScene(Scene scene) {
        trackedScenes.add(scene);
    }

    public static void reloadStyle() {
        for (Scene scene : trackedScenes)
            loadStyle(scene);
    }

    public static Style getStyle() {
        return style;
    }
}
