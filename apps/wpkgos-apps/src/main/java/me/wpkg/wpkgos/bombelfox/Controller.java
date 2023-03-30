package me.wpkg.wpkgos.bombelfox;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Controller {
    @FXML
    private TextField textUrl;

    @FXML
    private WebView webview;

    static String DEFAULT_PAGE="https://google.com";

    @FXML
    private void initialize() {
        WebEngine engine = webview.getEngine();
//        engine.setUserAgent("WPKG Browser 1.0 - AppleWebKit/555.99 JavaFX 8.0");
        textUrl.setText(DEFAULT_PAGE);

        textUrl.setOnKeyPressed((event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                engine.load(textUrl.getText());
            }
        });

        engine.locationProperty().addListener((url) -> {
            textUrl.setText(engine.getLocation());
        });

        engine.load(DEFAULT_PAGE);
    }
}
