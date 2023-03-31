package softwaredesign.GUI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DeathMechanism extends JLabel implements ProgressBarObserver {

    private static final List<DeathMechanism> instances = new ArrayList<>();

    boolean isActive;
    private final boolean isValIncreasing;
    private final JFrame frame;
    private final ObservableProgressBar progressBar;

    public DeathMechanism(boolean isValIncreasing, JFrame frame, ObservableProgressBar progressBar) {
        this.isValIncreasing = isValIncreasing;
        this.frame = frame;
        this.progressBar = progressBar;
        this.isActive = true;
        instances.add(this);
    }

    private void endGame() {
        frame.dispose();
        cleanUpBackgroundProcess();
        GUIEndGame.runGUI();
    }

    public void cleanUpBackgroundProcess() {
        for (DeathMechanism instance : instances) {
            this.progressBar.removeObserver(instance);
            this.isActive = false;
        }
    }

    @Override
    public void update(int newValue) {
        if (isValIncreasing && newValue >= 100 && isActive) {
            endGame();
        }

        if (!isValIncreasing && newValue <= 0 && isActive) {
            endGame();
        }
    }
}
