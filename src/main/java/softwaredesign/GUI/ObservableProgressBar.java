package softwaredesign.GUI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ObservableProgressBar extends JProgressBar {
    private final List<ProgressBarObserver> observers = new ArrayList<>();
    private final boolean valIncreasing;

    public ObservableProgressBar(int min, int max, boolean valIncreasing) {
        super(min, max);
        this.valIncreasing = valIncreasing;
    }

    public void addObserver(ProgressBarObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ProgressBarObserver observer) {
        observers.remove(observer);
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

    public boolean isValIncreasing() {
        return valIncreasing;
    }

}
