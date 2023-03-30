package me.wpkg.wpkgos.core.style;

public enum Style {
    LIGHT("/style/light.css"),
    DARK("/style/modena-dark.css","/style/dark.css");

    private final String[] styles;

    Style(String... styles) {
        this.styles = styles;
    }
    public String[] getStyles() {
        return styles;
    }

}
