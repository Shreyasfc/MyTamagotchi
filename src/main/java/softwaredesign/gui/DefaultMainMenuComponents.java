package softwaredesign.gui;

import javax.swing.*;
import java.awt.*;

public class DefaultMainMenuComponents implements ComponentFactory {

    @Override
    public JProgressBar createProgressBar(int startVal, String label, int yPos, Color fg, Color bg, int criticalValue, boolean isValIncreasing) {
        ObservableProgressBar progressBar = new ObservableProgressBar(0, 100, isValIncreasing);

        progressBar.setValue(startVal);
        progressBar.setStringPainted(true);
        progressBar.setString(label + ": " + progressBar.getValue() + "%");
        progressBar.setBounds(10, yPos, 150, 20);
        progressBar.setForeground(fg);
        progressBar.setBackground(bg);
        progressBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        progressBar.setName(label + "Bar");

        return progressBar;
    }

    @Override
    public JButton createButton(String text, int yPos) {
        JButton button = new JButton(text);
        button.setBounds(375, yPos, 100, 30);
        button.setPreferredSize(new Dimension(80, 30));
        return button;
    }

}
