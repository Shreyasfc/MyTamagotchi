package softwaredesign.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonClickActionCommand implements Command {
    private JFrame frame;
    private String progressBarToUpdate;
    private String animationImagePath;
    private int incrementVal;

    public ButtonClickActionCommand(JFrame frame, String progressBarToUpdate, String animationImagePath, int incrementVal) {
        this.frame = frame;
        this.progressBarToUpdate = progressBarToUpdate;
        this.animationImagePath = animationImagePath;
        this.incrementVal = incrementVal;
    }

    @Override
    public void execute() {
        Component[] components = frame.getContentPane().getComponents();
        disableAllButtons(components);
        visuallyUpdateProgressBar(components, progressBarToUpdate, incrementVal, frame);
        JLabel feedingLabel = addLabelWithImageAndReturn(frame, animationImagePath);
        animateAndReEnableButtons(frame, components, feedingLabel);
    }

    private static void disableAllButtons(Component[] components) {
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.setEnabled(false);
            }
        }
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

    private static JLabel addLabelWithImageAndReturn(JFrame frame, String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        JLabel label = new JLabel(icon);
        label.setBounds(200, 30, icon.getIconWidth(), icon.getIconHeight());
        label.setHorizontalAlignment(0);
        frame.add(label);
        return label;
    }

    private static void animateAndReEnableButtons(JFrame frame, Component[] components, JLabel feedingLabel) {

        Timer timer = new Timer(50, null);
        timer.addActionListener(new ActionListener() {
            int count = 0;
            final int maxCount = 8;
            int yPosition = 30;

            public void actionPerformed(ActionEvent e) {
                count++;
                yPosition += 18;
                feedingLabel.setLocation(200, yPosition);

                if (count >= maxCount) {
                    timer.stop();
                    frame.remove(feedingLabel);
                    frame.revalidate();
                    frame.repaint();
                    reEnableAllButtons(components);
                }
            }
        });

        timer.start();

    }

    private static void reEnableAllButtons(Component[] components) {
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.setEnabled(true);
            }
        }
    }

}

