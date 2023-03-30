package softwaredesign.GUI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

class ObservableProgressBar extends JProgressBar {
    private final List<ProgressBarObserver> observers = new ArrayList<>();

    public ObservableProgressBar(int min, int max) {
        super(min, max);
    }

    public void addObserver(ProgressBarObserver observer) {
        observers.add(observer);
    }

    @Override
    public void setValue(int n) {
        super.setValue(n);
        notifyObservers();
    }

    private void notifyObservers() {
        for (ProgressBarObserver observer : observers) {
            observer.update(getValue());
        }
    }
}
