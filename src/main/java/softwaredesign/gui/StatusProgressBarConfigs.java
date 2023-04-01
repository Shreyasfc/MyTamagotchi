package softwaredesign.gui;

import java.awt.*;

class StatusProgressBarConfigs {

    int startVal;
    String label;
    int yPos;
    Color fg;
    Color bg;
    int criticalValue;
    boolean isValIncreasing;

    public StatusProgressBarConfigs(int startVal, String label, int yPos, Color fg, Color bg, int criticalValue, boolean isValIncreasing) {
        this.startVal = startVal;
        this.label = label;
        this.yPos = yPos;
        this.fg = fg;
        this.bg = bg;
        this.criticalValue = criticalValue;
        this.isValIncreasing = isValIncreasing;
    }

}
