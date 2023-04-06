package softwaredesign.gui;

import javax.swing.*;
import java.awt.*;

public interface ComponentFactory {

    JProgressBar createProgressBar(int value, String text, int y, Color fg, Color bg, int criticalValue, boolean isValIncreasing);
    JButton createButton(String text, int y);

}
