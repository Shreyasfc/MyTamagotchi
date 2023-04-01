package softwaredesign.gui;

import javax.swing.*;
import java.awt.*;

public interface ComponentFactory {

    JProgressBar createProgressBar(int startVal, String label, int yPos, Color fg, Color bg, int criticalValue, boolean isValIncreasing);
    JButton createButton(String text, int yPos);

}
