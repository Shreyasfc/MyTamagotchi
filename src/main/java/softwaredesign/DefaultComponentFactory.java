package softwaredesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DefaultComponentFactory implements ComponentFactory {

    @Override
    public JProgressBar createProgressBar(int value, String text, int y, Color fg, Color bg, int criticalValue, boolean isValIncreasing) {
        JProgressBar progressBar = new JProgressBar(0, 100);
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
    public JButton createButton(String text, int y, ActionListener action) {
        JButton button = new JButton(text);
        button.setBounds(375, y, 100, 30);
        button.setPreferredSize(new Dimension(80, 30));
        button.addActionListener(action);

        return button;
    }
}
