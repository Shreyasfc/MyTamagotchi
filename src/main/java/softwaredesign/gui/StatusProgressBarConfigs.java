package softwaredesign.gui;

import java.awt.*;

class StatusProgressBarConfigs {

    int value;
    String text;
    int y;
    Color fg;
    Color bg;
    int criticalValue;
    boolean isValIncreasing;

    public StatusProgressBarConfigs(int value, String text, int y, Color fg, Color bg, int criticalValue, boolean isValIncreasing) {
        this.value = value;
        this.text = text;
        this.y = y;
        this.fg = fg;
        this.bg = bg;
        this.criticalValue = criticalValue;
        this.isValIncreasing = isValIncreasing;
    }

}
