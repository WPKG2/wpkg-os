module me.wpkg.wpkgos.apps {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    opens me.wpkg.wpkgos.bombelfox to javafx.fxml;
    exports me.wpkg.wpkgos.bombelfox;
}