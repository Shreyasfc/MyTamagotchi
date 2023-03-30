package softwaredesign.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public interface ComponentFactory {
    JProgressBar createProgressBar(int value, String text, int y, Color fg, Color bg, int criticalValue, boolean isValIncreasing);
    JButton createButton(String text, int y, ActionListener action);
}
