package softwaredesign.gui;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class DeathController extends JLabel implements ProgressBarObserver {

    private final AtomicBoolean stopSignal;
    private final boolean isValIncreasing;
    private final JFrame frame;

    public DeathController(boolean isValIncreasing, JFrame frame, AtomicBoolean stopSignal) {
        this.isValIncreasing = isValIncreasing;
        this.frame = frame;
        this.stopSignal = stopSignal;
    }

    private void endGame() {
        frame.dispose();
        stopSignal.compareAndSet(false, true);
    }

    @Override
    public void update(int newValue) {
        if (isValIncreasing && newValue >= 100) {
            endGame();
        }

        if (!isValIncreasing && newValue <= 0) {
            endGame();
        }
    }
}
