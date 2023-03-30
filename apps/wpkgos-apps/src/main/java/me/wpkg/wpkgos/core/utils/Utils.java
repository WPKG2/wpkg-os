package me.wpkg.wpkgos.core.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import me.wpkg.wpkgos.core.style.StyleManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class Utils {
    public static String getWorkDir() {
        String osname = System.getProperty("os.name").toLowerCase();
        if (osname.startsWith("windows"))
            return Paths.get(System.getenv("APPDATA"), "wpkg2-cli").toString();
        else if (osname.contains("nux") || osname.contains("freebsd"))
            return Paths.get(System.getProperty("user.home"), ".config", "wpkg2-cli").toString();
        else if (osname.contains("mac") || osname.contains("darwin"))
            return Paths.get(System.getProperty("user.home"), "Library", "Application Support", "wpkg2-cli").toString();
        return "";
    }

    public static void dialog(String title,String text,Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        StyleManager.loadStyle(alert.getDialogPane());

        alert.show();
    }

    public static Optional<ButtonType> confirmDialog(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, text, ButtonType.YES, ButtonType.CANCEL);
        alert.setTitle(title);
        alert.setHeaderText(null);
        StyleManager.loadStyle(alert.getDialogPane());
        return alert.showAndWait();
    }

    public static Optional<ButtonType> customConfirmDialog(String title, String text,ButtonType... types) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, text,types);
        alert.setTitle(title);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setHeaderText(null);
        StyleManager.loadStyle(alert.getDialogPane());
        return alert.showAndWait();
    }

    public static Optional<String> inputDialog(String title, String text) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(text);
        StyleManager.loadStyle(dialog.getDialogPane());

        return dialog.showAndWait();
    }

    public static double roundTo2DecimalPlace(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String bytesToHex(byte[] hash)
    {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String sha256FromFile(File file) throws IOException
    {
        try (FileInputStream fos = new FileInputStream(file)){
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return bytesToHex(digest.digest(fos.readAllBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
