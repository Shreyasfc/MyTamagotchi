package softwaredesign.gui;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObservableProgressBar extends JProgressBar {
    private final List<ProgressBarObserver> observers = new ArrayList<>();
    private final boolean isValIncreasing;

    public ObservableProgressBar(int min, int max, boolean isValIncreasing) {
        super(min, max);
        this.isValIncreasing = isValIncreasing;
    }

    public void addObserver(ProgressBarObserver observer) {
        observers.add(observer);
    }

    @Override
    public void setValue(int n) {

        super.setValue(n);
        try {
            notifyObservers();
        } catch (IOException e) {
            throw new GuiCloseException("Failed to close GUI", e);
        }

    }

    private void notifyObservers() throws IOException {
        for (ProgressBarObserver observer : observers) {
            observer.update(getValue());
        }
    }

    public boolean isValIncreasing() {
        return isValIncreasing;
    }

}
