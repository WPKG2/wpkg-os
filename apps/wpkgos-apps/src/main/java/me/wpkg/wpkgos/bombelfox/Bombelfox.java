package me.wpkg.wpkgos.bombelfox;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.wpkg.wpkgos.core.style.Style;
import me.wpkg.wpkgos.core.style.StyleManager;

import java.io.IOException;

public class Bombelfox extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Bombelfox.class.getResource("/fxml/bombelfox-main.fxml"));
        StyleManager.init();

        Scene scene = new Scene(fxmlLoader.load(), 1280,720);
        StyleManager.setStyle(Style.DARK);
        StyleManager.trackScene(scene);
        StyleManager.reloadStyle();

        stage.setTitle("Bombelfox");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}