package softwaredesign.GUI;

import javax.swing.*;
import java.awt.*;

public class UpdateProgressBarCommand implements Command {
    private Component[] components;
    private String progressBarToUpdate;
    private int incrementVal;
    private JFrame frame;

    public UpdateProgressBarCommand(Component[] components, String progressBarToUpdate, int incrementVal, JFrame frame) {
        this.components = components;
        this.progressBarToUpdate = progressBarToUpdate;
        this.incrementVal = incrementVal;
        this.frame = frame;
    }

    @Override
    public void execute() {
        visuallyUpdateProgressBar(components, progressBarToUpdate, incrementVal, frame);
    }

    private void visuallyUpdateProgressBar(Component[] components, String progressBarToUpdate, int incrementVal, JFrame frame) {

        for (Component component : components) {
            if (component instanceof JProgressBar) {
                JProgressBar progressBar = (JProgressBar) component;
                if (progressBar.getString().startsWith(progressBarToUpdate)) {
                    int newValue = progressBar.getValue() + incrementVal;
                    newValue = Math.max(0, Math.min(100, newValue));
                    progressBar.setValue(newValue);
                    progressBar.setString(progressBarToUpdate + ": " + newValue + "%");
                    frame.revalidate();
                    frame.repaint();
                }
            }
        }

    }

}


