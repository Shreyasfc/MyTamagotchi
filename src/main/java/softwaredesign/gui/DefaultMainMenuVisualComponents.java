package softwaredesign.gui;

import javax.swing.*;
import java.awt.*;

public class DefaultMainMenuVisualComponents implements ComponentFactory {

    @Override
    public JProgressBar createProgressBar(int value, String text, int y, Color fg, Color bg, int criticalValue, boolean isValIncreasing) {
        ObservableProgressBar progressBar = new ObservableProgressBar(0, 100, isValIncreasing);

        progressBar.setValue(value);
        progressBar.setStringPainted(true);
        progressBar.setString(text + ": " + progressBar.getValue() + "%");
        progressBar.setBounds(10, y, 150, 20);
        progressBar.setForeground(fg);
        progressBar.setBackground(bg);
        progressBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        progressBar.setName(text + "Bar");

        return progressBar;
    }

    @Override
    public JButton createButton(String text, int y) {
        JButton button = new JButton(text);
        button.setBounds(375, y, 100, 30);
        button.setPreferredSize(new Dimension(80, 30));
        return button;
    }

}
