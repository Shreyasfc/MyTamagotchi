package softwaredesign.GUI;

import javax.swing.*;

class WarningLabel extends JLabel implements ProgressBarObserver {
    private final int criticalValue;
    private final boolean isValIncreasing;

    public WarningLabel(String text, int criticalValue, boolean isValIncreasing) {
        super(text);
        this.criticalValue = criticalValue;
        this.isValIncreasing = isValIncreasing;
    }

    @Override
    public void update(int newValue) {
        setVisible((isValIncreasing && newValue >= criticalValue) || (!isValIncreasing && newValue <= criticalValue));
    }
}
