package softwaredesign.gui;

import javax.swing.*;
import java.awt.*;

class WarningLabel extends JLabel implements ProgressBarObserver {
    private final int criticalValue;
    private final boolean isValIncreasing;

    public WarningLabel(String text, int criticalValue, boolean isValIncreasing, ObservableProgressBar progressBar) {
        super(text + " Warning!");
        this.criticalValue = criticalValue;
        this.isValIncreasing = isValIncreasing;
        setName(text + "Warning");
        setBounds(progressBar.getX() + progressBar.getWidth() + 5, progressBar.getY(), 100, 20);
        setForeground(Color.RED);
        setVisible(false);
    }

    @Override
    public void update(int newValue) {
        setVisible((isValIncreasing && newValue >= criticalValue) || (!isValIncreasing && newValue <= criticalValue));
    }

}
